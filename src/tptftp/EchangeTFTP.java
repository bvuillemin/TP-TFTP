package tptftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public void sendPacket(PacketTFTP packet) {
        byte[] data = packet.getDatagram();
        
        DatagramPacket dp = new DatagramPacket(data, data.length, adresseIP, portUDP);
        try {
            this.socket.send(dp);
        } catch (IOException ex) {
            System.out.println("Impossible d'envoyer le packet TFTP");
        }
    }

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

    public boolean receiveAck(int n) throws Exception{
        try {
            return PacketAck.isAckPacket(receivePacket());
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void sendAck(int n) {
        PacketAck ack = new PacketAck(n);
        sendPacket(ack);
    }
    
    public void sendError(int n, String message) {
        PacketError err = new PacketError(n, message);
        sendPacket(err);
    }
    
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

    public FileInputStream openReadFile(String file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            System.out.println("Impossible d'ouvrir le fichier en lecture");
        }
        return null;
    }

    public FileOutputStream openWriteFile(String file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            System.out.println("Impossible d'ouvrir le fichier en écriture");
        }
        return null;
    }

    public boolean closeReadFile(FileInputStream f) {
        try {
            f.close();
            return true;
        } catch (IOException ex) {
            System.out.println("Impossible de fermer le fichier en lecture");
            return false;
        }
    }

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
