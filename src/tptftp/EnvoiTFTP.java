package tptftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import packetTFTP.*;

/**
 *
 * @author Dimitri
 */
public class EnvoiTFTP extends EchangeTFTP {

    public EnvoiTFTP(InetAddress _ip) {
        super();
        this.portUDP = 69;
        this.adresseIP = _ip;
    }

    public boolean sendData(String nomFichier) {
        PacketData data;
        int b = 0;
        byte[] buffer = new byte[512];
        int i = 1;
        try {
            FileInputStream fe = openReadFile(nomFichier);
            while (fe.read(buffer) != -1) {
                data=new PacketData(i,buffer);
                if (!trySendPacket(data, i)) {
                    System.out.println("Impossible d'envoyer le packet "+ i);
                    return false;
                }
                i++;
            }
            if (fe.available() >= 0) {
                buffer=new byte[fe.available()];
                fe.read(buffer);
                data = new PacketData(i,buffer);
                if (!trySendPacket(data, i)) {
                    System.out.println("Impossible d'envoyer le packet "+ i);
                    return false;
                }
            }
            closeReadFile (fe);
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Fichier non trouvé : ");
            Logger.getLogger(EnvoiTFTP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(EnvoiTFTP.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int SendFile(String nomFichier, String adresse) {
        try {
            adresseIP = InetAddress.getByName(adresse);
        } catch (UnknownHostException ex) {
            Logger.getLogger(EnvoiTFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        File f = new File (nomFichier);
        String name=f.getName();
        PacketWRQ packet = new PacketWRQ("netascii", name);
        if (trySendPacket(packet, 0)) {
            System.out.println("Demande d'envoi acceptée");
            if (!sendData(nomFichier)){
                System.out.println("L'envoi a échoué");
            }
        } else {
            System.out.println("Serveur inaccessible");
        }
        return 0;
    }

    @Override
    public void run() {
        SendFile("", "");
    }
}
