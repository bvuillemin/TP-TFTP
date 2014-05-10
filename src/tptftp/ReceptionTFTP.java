package tptftp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import packetTFTP.*;

public class ReceptionTFTP extends EchangeTFTP {

    private String fileName;
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
     */
    public boolean tryReceiveDataPacket (PacketData packet) {
        byte[] buffer;
        
        for (int i = 0; i < NB_TENTATIVE; i++) {
            try {
                buffer = receiveDataPacket();
            } catch (Exception ex) {
                return false;
            }
            
            try {
            String str = new String(buffer, "US-ASCII");
            System.out.println(str + " = " + Arrays.toString(buffer));
            } catch (UnsupportedEncodingException ex) {
            }

            if (packet.getDatagramPacket(buffer) || PacketError.isErrorPacket(buffer)) {
                if (!PacketError.isErrorPacket(buffer)) {
                    sendAck(packet.getBlock());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Ecrit dans le fichier le premier paquet de DATA puis essaie de recevoir les suivants jusqu'à la fin de l'envoi
     * @param data 
     */
    public void receiveData(PacketData data) {
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
     * Envoie du paquet RRQ
     * @param data Premier paquet de data que l'on reçoit en temps qu'ACK
     * @return 
     */
    public boolean trySendRRQ(PacketData data) {
        PacketRRQ packet = new PacketRRQ("netascii", fileName);
        for (int i = 0; i < NB_TENTATIVE; i++) {
            sendPacket(packet);
            if (tryReceiveDataPacket(data)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Appelle l'envoi d'un paquet RRQ, puis effectue l'appel pour la réception des données
     * @return 0 Si correctement effectué
     */
    public int ReceiveFile() {
        PacketData data = new PacketData();
        if (trySendRRQ(data)) {
            receiveData(data);
        } else {
            System.out.println("Serveur inaccessible");
        }
        return 0;
    }

    @Override
    public void run() {
        ReceiveFile();
    }
}
