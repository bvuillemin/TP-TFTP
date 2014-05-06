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

    public void sendData(String nomFichier) {
        PacketData data;
        int b = 0;
        byte[] buffer = new byte[512];
        int i = 1;

        try {
            while(b != -1){
                FileInputStream fe = new FileInputStream(nomFichier);
                for (int j = 0; j < buffer.length; j++) {
                    b = fe.read();
                    if (b == -1) {
                        break;
                    }
                    buffer[i] = (byte) b;
                }
                data = new PacketData(i, buffer);
                trySendPacket(data, i);
                i++;
            }
            
            //Pour l'instant envoi seulement du début du fichier

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EnvoiTFTP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EnvoiTFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int SendFile(String nomFichier, String adresse) {
        try {
            adresseIP = InetAddress.getByName(adresse);
        } catch (UnknownHostException ex) {
            Logger.getLogger(EnvoiTFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        PacketWRQ packet = new PacketWRQ("netascii", nomFichier);
        if (trySendPacket(packet, 0)) {
            sendData(nomFichier);
        } else {
            //server not reachable
        }
        return 0;
    }

    @Override
    public void run() {
        SendFile("", "");
    }
}
