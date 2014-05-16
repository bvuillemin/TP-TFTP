package tptftp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import packetTFTP.*;

public class ReceptionTFTP extends EchangeTFTP {

    private String fileName;
    private final String path;

    public ReceptionTFTP(String _path) {
        super();
        this.portUDP = 69;
        this.path = _path;
    }

    /**
     * Attend de recevoir un paquet de DATA et envoit l'ACK nécessaire
     *
     * @param packet
     * @throws packetTFTP.ErreurTFTP
     */
    public void receiveDataPacket(PacketData packet) throws ErreurTFTP {
        byte[] buffer;
        try {
            buffer = receiveDataPacket();
            if (!packet.getDatagramPacket(buffer)) {
                PacketError err = new PacketError();
                if (err.getDatagramPacket(buffer)) {
                    throw new ErreurTFTP(5, "Erreur reçue " + err.getErrorCode() + " : " + err.getErrMsg());
                } 
                else {
                    throw new ErreurTFTP(6, "Packet non reconnu ou non attendu");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        sendAck(packet.getBlock());
    }

    /**
     * Ecrit dans le fichier le premier paquet de DATA puis essaie de recevoir
     * les suivants jusqu'à la fin de l'envoi
     *
     * @param data
     * @throws packetTFTP.ErreurTFTP
     */
    public void receiveData(PacketData data) throws ErreurTFTP {
        boolean receptionOK = true;
        int numData = data.getBlock(), i = 0;
        FileOutputStream f = openWriteFile(path + fileName);
        if (f != null) {
            try{
                f.write(data.getData());
            }
            catch (IOException ex) {
                throw new ErreurTFTP(2, "Impossible d'écrire dans le fichier : " + fileName);
            }
            do {
                for (i = 0; i < NB_TENTATIVE; i++) {
                    try {
                        receiveDataPacket(data);
                        receptionOK = data.getBlock() != numData;
                        numData = data.getBlock();
                        f.write(data.getData());
                        break;
                    } catch (ErreurTFTP er) {
                        if (i >= NB_TENTATIVE) {
                            throw new ErreurTFTP(3, "Aucune réponse du serveur : Time Out");
                        } else {
                            if (er.getErrType() == 5) {
                                throw er;
                            } else if (er.getErrType() == 6) {
                                sendError(data.getBlock()+1,er.getMessage());
                                System.out.println("Erreur reçue : " + er.getMessage());
                            } else {
                                System.out.println(er.getMessage());
                            }
                        }
                    }
                    catch (IOException ex) {
                        throw new ErreurTFTP(2, "Impossible d'écrire dans le fichier : " + fileName);
                    }
                }
            }while (data.getData().length >= 512 && receptionOK);
        } else {
            throw new ErreurTFTP(2, "Impossible de creer le fichier : " + fileName);
        }
        if (!closeWriteFile(f)) {
            throw new ErreurTFTP(2, "Impossible de fermer le fichier : " + fileName);
        }
    }

    /**
     * Réception des paquets liés à l'envoi de données
     *
     * @return
     * @throws packetTFTP.ErreurTFTP
     */
    public byte[] receiveDataPacket() throws Exception {
        byte[] buffer = new byte[516];
        DatagramPacket dtg = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(dtg);
        } catch (IOException ex) {
            throw new Exception("Aucun Packet reçu");
        }
        if (dtg.getPort() != portUDP) {
            portUDP = dtg.getPort();
        }
        return dtg.getData();
    }

    /**
     * Envoi du paquet RRQ
     *
     * @param data Premier paquet de data que l'on reçoit en temps qu'ACK
     * @throws packetTFTP.ErreurTFTP
     */
    public void trySendRRQ(PacketData data) throws ErreurTFTP {
        int i;
        PacketRRQ packet = new PacketRRQ("netascii", fileName);
        for (i = 0; i < NB_TENTATIVE; i++) {
            sendPacket(packet);
            receiveDataPacket(data);
            break;
        }
        if (i >= NB_TENTATIVE) {
            throw new ErreurTFTP(3, "Aucune réponse du serveur : Time Out");
        }
    }

    /**
     * Appelle l'envoi d'un paquet RRQ, puis effectue l'appel pour la réception
     * des données
     *
     * @param _file
     * @param adresse
     * @return 0 Si correctement effectué 1 Adresse IP incorrecte 2 Erreur
     * Fichier 3 Aucune réponse du serveur : Time Out 4 Erreur envoi ACK 5
     * Erreur dans la réception des données 6 Erreur dans l'envoi de la demande
     */
    public int ReceiveFile(String _file, String adresse) {
        PacketData data = new PacketData();

        this.fileName = _file;
        try {
            adresseIP = InetAddress.getByName(adresse);
        } catch (UnknownHostException ex) {
            System.out.println("Adresse Ip incorrecte");
            return 1;
        }
        try {
            trySendRRQ(data);
        } catch (ErreurTFTP er) {
            System.out.println("Demande RRQ refusée : " + er.getMessage());
            return er.getErrType();
        }

        try {
            receiveData(data);
        } catch (ErreurTFTP ex) {
            System.out.println("La reception a échoué : " + ex.getMessage());
            return ex.getErrType();
        }
        System.out.println("La reception a réussi");
        return 0;
    }

    @Override
    public void run() {
        ReceiveFile("", "");
    }
}
