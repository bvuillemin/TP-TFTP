package tptftp;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import packetTFTP.*;

public class EnvoiTFTP extends EchangeTFTP {

    public EnvoiTFTP() {
        super();
        this.portUDP = 69;
    }
    
    /**
     * Envoi un paquet jusqu'à ce que l'acquittement se fasse ou que le nombre de tentatives soit dépassé
     * @param packet
     * @param n 
     * @throws packetTFTP.ErreurTFTP 
     */
    public void trySendPacket(PacketTFTP packet, int n) throws ErreurTFTP {
        byte[] _packet;
        int i;
        for (i = 0; i < NB_TENTATIVE; i++) {
            try{
                sendPacket(packet);
            }
            catch(ErreurTFTP er){
                throw new ErreurTFTP (4,er.getMessage()+ n);
            }
            try {
                _packet = receivePacket();
                if(!PacketAck.isAckPacket(_packet, n)){
                    try{
                        PacketError err = new PacketError();
                        err.getDatagramPacket(_packet);
                        throw new ErreurTFTP(5,"Erreur reçue " + err.getErrorCode() + " : " + err.getErrMsg());
                    }
                    catch(Exception ex){
                        throw new ErreurTFTP(6,"Packet non reconnu ou non attendu");
                    }
                }
                else break;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        if (i>=NB_TENTATIVE){
            throw new ErreurTFTP (3,"Impossible d'envoyer le paquet : " + n + " après " + NB_TENTATIVE + " tentatives.");
        }
    }
    
    /**
     * Va s'occuper de l'envoi du fichier uniquement
     *
     * @param nomFichier
     * @throws packetTFTP.ErreurTFTP
     */
    public void sendData(String nomFichier) throws ErreurTFTP {
        PacketData data;
        byte[] buffer = new byte[512];
        int i = 1;
        try {
            FileInputStream fe = openReadFile(nomFichier);
            while (fe.read(buffer) != -1) {
                data = new PacketData(i, buffer);
                trySendPacket(data, i);
                i++;
            }
            if (fe.available() >= 0) {
                buffer = new byte[fe.available()];
                fe.read(buffer);
                data = new PacketData(i, buffer);
                trySendPacket(data, i);
            }
            if(!closeReadFile(fe)){
                throw new ErreurTFTP(2,"Impossible d'ouvrir le fichier : " + nomFichier);
            }
        } catch (Exception ex) {
            throw new ErreurTFTP(2,"Impossible de lire le fichier : " + nomFichier);
        }
    }
    
    /**
     * 
     * @param name
     * @throws ErreurTFTP 
     */
    public void trySendWRQ(String name) throws ErreurTFTP {
        PacketWRQ packet = new PacketWRQ("netascii", name);
        trySendPacket(packet, 0);
    }

    /**
     * Primitive de service pour envoyer un fichier
     *
     * @param nomFichier
     * @param adresse
     * @return 
     * 0 L'envoi s'est bien effectué 
     * 1 Adresse IP incorrecte 
     * 2 Erreur Fichier 
     * 3 Aucune réponse du serveur : Time Out 
     * 4 Erreur envoi Packet
     * 5 Erreur reçue
     * 6 Packet non reconnu reçu
     */
    public int SendFile(String nomFichier, String adresse) {
        try {
            adresseIP = InetAddress.getByName(adresse);
        } catch (UnknownHostException ex) {
            System.out.println("Adresse Ip incorrecte");
            return 1;
        }
        File f = new File(nomFichier);
        try {
            trySendWRQ(f.getName());
        } catch (ErreurTFTP er) {
            System.out.println("Demande WRQ refusée : " + er.getMessage());
            return er.getErrType();
        }
        try {
            sendData(nomFichier);
        } catch (ErreurTFTP ex) {
            System.out.println("L'envoi a échoué : " + ex.getMessage());
            return ex.getErrType();
        }
        System.out.println("L'envoi a réussi");
        return 0;
    }

    @Override
    public void run() {
        SendFile("", "");
    }
}
