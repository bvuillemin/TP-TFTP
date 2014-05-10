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

    static int scanPorts(int debut, int fin) {
        DatagramSocket port;
        for (int i = debut; i <= fin; i++) {
            try {
                port = new DatagramSocket(i);
                return i;
            } catch (SocketException e) {
            }
        }
        return 0;
    }

    /**
     * Envoie un paquet via le protocole TCP
     * @param packet
     * @throws Exception 
     */
    public void sendPacket(PacketTFTP packet) throws Exception{
        byte[] data = packet.getDatagram();
        
        DatagramPacket dp = new DatagramPacket(data, data.length, adresseIP, portUDP);
        try {
            this.socket.send(dp);
        } catch (IOException ex) {
            throw new Exception("Impossible de joindre le serveur");
        }
    }

    /**
     * Se met en attente de réception d'un fichier pendant un temps défini
     * @return
     * @throws Exception 
     */
    public byte[] receivePacket() throws Exception{
        byte[] buffer = new byte[1024];
        DatagramPacket dtg = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(dtg);
        } catch (IOException ex) {
            throw new Exception("Erreur de réception");
        }
        if (dtg.getPort() != portUDP) {
            portUDP = dtg.getPort();
        }
        return dtg.getData();
    }

    /**
     * Retourne vrai si le paquet reçu est un paquet d'ACK
     * @param n
     * @return
     * @throws Exception 
     */
    public boolean receiveAck(int n) throws Exception{
        try {
            return PacketAck.isAckPacket(receivePacket(), n);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Envoi d'un ACK 
     * @param n Numéro de bloc
     * @throws Exception 
     */
    public void sendAck(int n) throws Exception {
        PacketAck ack = new PacketAck(n);
        try {
            sendPacket(ack);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    /**
     * Envoi d'un paquet d'erreur
     * @param n
     * @param message
     * @throws Exception 
     */
    public void sendError(int n, String message) throws Exception {
        PacketError err = new PacketError(n, message);
        try {
            sendPacket(err);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    /**
     * Envoi un paquet jusqu'à ce que l'acquittement se fasse ou que le nombre de tentatives soit dépassé
     * @param packet
     * @return
     * @throws Exception 
     */
    public boolean trySendPacket(PacketTFTP packet, int n) throws Exception {
        for (int i = 0; i < NB_TENTATIVE; i++) {
            sendPacket(packet);
            try {
                if (receiveAck(n)) {
                    return true;
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
        return false;
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
