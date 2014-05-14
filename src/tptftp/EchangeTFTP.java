package tptftp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import packetTFTP.*;

public abstract class EchangeTFTP implements Runnable {

    protected InetAddress adresseIP;
    protected int portUDP;
    protected DatagramSocket socket;

    final int NB_TENTATIVE = 3;
    
    
    public EchangeTFTP() {
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(5000);
        } catch (SocketException ex) {
            System.err.println("Impossible de créer le socket");
        }
    }
    
    /**
     * Envoie un paquet via le protocole TCP
     * @param packet 
     * @throws packetTFTP.ErreurTFTP 
     */
    public void sendPacket(PacketTFTP packet) throws ErreurTFTP{
        byte[] data = packet.getDatagram();
        
        DatagramPacket dp = new DatagramPacket(data, data.length, adresseIP, portUDP);
        try {
            this.socket.send(dp);
        } catch (IOException ex) {
            throw new ErreurTFTP(4,"Impossible d'envoyer le packet ");
        }
    }

    /**
     * Se met en attente de réception d'un fichier pendant un temps défini
     * @return 
     * @throws packetTFTP.ErreurTFTP 
     */
    public byte[] receivePacket() throws Exception{
        byte[] buffer = new byte[1024];
        DatagramPacket dtg = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(dtg);
        } catch (IOException ex) {
            throw new Exception("Aucun packet reçu");
        }
        if (dtg.getPort() != portUDP) {
            portUDP = dtg.getPort();
        }
        return dtg.getData();
    }

    /**
     * Envoi d'un ACK 
     * @param n Numéro de bloc 
     * @throws packetTFTP.ErreurTFTP 
     */
    public void sendAck(int n) throws ErreurTFTP {
        PacketAck ack = new PacketAck(n);
        try {
            sendPacket(ack);
        } catch (Exception ex) {
            throw new ErreurTFTP(4,"Impossible d'envoyer l'ACK : " + ack.getBlock());
        }
    }
    
    /**
     * Envoi d'un paquet d'erreur
     * @param n
     * @param message 
     * @throws packetTFTP.ErreurTFTP 
     */
    public void sendError(int n, String message) throws ErreurTFTP {
        PacketError err = new PacketError(n, message);
        try {
            sendPacket(err);
        } catch (Exception ex) {
            throw new ErreurTFTP(4,"Impossible d'envoyer l'Erreur : " + message);
        }
    }

    /**
     * Ouvre un fichier en lecture
     * @param file
     * @return 
     */
    public FileInputStream openReadFile(String file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            System.out.println("Impossible d'ouvrir le fichier en lecture");
        }
        return null;
    }

    /**
     * Ouvre un fichier en écriture
     * @param file
     * @return 
     */
    public FileOutputStream openWriteFile(String file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            System.out.println("Impossible d'ouvrir le fichier en écriture");
        }
        return null;
    }

    /**
     * Ferme un fichier en lecture
     * @param f
     * @return 
     */
    public boolean closeReadFile(FileInputStream f) {
        try {
            f.close();
            return true;
        } catch (IOException ex) {
            System.out.println("Impossible de fermer le fichier en lecture");
            return false;
        }
    }

    /**
     * Ferme un fichier en écriture
     * @param f
     * @return 
     */
    public boolean closeWriteFile(FileOutputStream f) {
        try {
            f.close();
            return true;
        } catch (IOException ex) {
            System.out.println("Impossible de fermer le fichier en écriture");
            return false;
        }
    }
}
