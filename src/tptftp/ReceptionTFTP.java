package tptftp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import packetTFTP.*;

public class ReceptionTFTP extends EchangeTFTP {

    private final String fileName;
    private final String path;

    public ReceptionTFTP(InetAddress _ip, String _file, String _path) {
        super();
        this.portUDP = 69;
        this.adresseIP = _ip;
        this.fileName = _file;
        this.path = _path;
    }

    /**
     * Attend de recevoir un paquet de DATA et envoit l'ACK nécessaire
     * @param packet
     * @return Vrai si la réception s'est effectuée
     * @throws Exception indiquant la provenance de l'erreur
     */
    public boolean tryReceiveDataPacket (PacketData packet) throws Exception {
        byte[] buffer;
        
        for (int i = 0; i < NB_TENTATIVE; i++) {
            try {
                buffer = receiveDataPacket();
            } catch (Exception ex) {
                return false;
            }
            
            if (packet.getDatagramPacket(buffer) || PacketError.isErrorPacket(buffer)) {
                if (!PacketError.isErrorPacket(buffer)) {
                    try {
                        sendAck(packet.getBlock());
                    } catch (Exception ex) {
                        throw ex;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Ecrit dans le fichier le premier paquet de DATA puis essaie de recevoir les suivants jusqu'à la fin de l'envoi
     * @param data 
     * @throws Exception indiquant la provenance de l'erreur
     */
    public void receiveData(PacketData data) throws Exception{
        boolean receptionOK = true;
        data.afficherPacket();
        FileOutputStream f = openWriteFile(path + fileName);
        if (f != null) {
            try {
                f.write(data.getData());
                while (data.getData().length >= 512 && receptionOK) {
                    receptionOK = tryReceiveDataPacket(data);
                    f.write(data.getData());
                }
            } catch (IOException ex) {
                System.out.println("Impossible d'écrire dans le fichier " + fileName);
            }
        }
        closeWriteFile(f);
    }
    
    /**
     * Réception des paquets liés à l'envoi de données
     * @return
     * @throws Exception 
     */
    public byte[] receiveDataPacket() throws Exception{
        byte[] buffer = new byte[516];
        DatagramPacket dtg = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(dtg);
        } catch (IOException ex) {
            throw ex;
        }
        if (dtg.getPort() != portUDP) {
            portUDP = dtg.getPort();
        }
        return dtg.getData();
    }

    /**
     * Envoie du paquet RRQ
     * @param data Premier paquet de data que l'on reçoit en temps qu'ACK
     * @return 
     * @throws Exception indiquant la provenance de l'erreur
     */
    public boolean trySendRRQ(PacketData data) throws Exception {
        PacketRRQ packet = new PacketRRQ("netascii", fileName);
        for (int i = 0; i < NB_TENTATIVE; i++) {
            try {
                sendPacket(packet);
            } catch (Exception ex) {
                throw ex;
            }
            if (tryReceiveDataPacket(data)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Appelle l'envoi d'un paquet RRQ, puis effectue l'appel pour la réception des données
     * @return 0 Si correctement effectué
     *         1 Erreur dans l'envoi de la demande
     *         2 Erreur dans la réception des données
     */
    public int ReceiveFile() {
        PacketData data = new PacketData();
        
        try {
            trySendRRQ(data);
        } catch (Exception ex) {
            return 1;
        }
        
        try {
            receiveData(data);
        } catch (Exception ex) {
            return 2;
        }
        return 0;
    }

    @Override
    public void run() {
        ReceiveFile();
    }
}
