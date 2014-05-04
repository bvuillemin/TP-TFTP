/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tptftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import packetTFTP.*;

/**
 *
 * @author Dimitri
 */
public abstract class EchangeTFTP implements Runnable {
    protected InetAddress adresseIP;
    protected int portUDP;
    protected DatagramSocket socket;
    
    final int NB_TENTATIVE=3;
    
    public EchangeTFTP (){
        try{
            socket = new DatagramSocket ();
            socket.setSoTimeout(20000);
        }
        catch (SocketException ex) {
            System.err.println("Impossible de créer le socket");
        }
    }
    
    static int scanPorts(int debut, int fin) {
        DatagramSocket port;
        for (int i = debut; i <= fin; i++) {
            try {
                port = new DatagramSocket(i);
                return i;
            } 
            catch (SocketException e) {
            }
        }
        return 0;
    }
    
    public void sendPacket (PacketTFTP packet){
        byte[] data = packet.getDatagram();
        DatagramPacket dp = new DatagramPacket (data, data.length,adresseIP, portUDP);
        try{
            this.socket.send(dp);
        }
        catch (IOException ex){
            System.out.println("Impossible d'envoyer le packet TFTP");
        }
    }
    
    public byte[] receivePacket (){
        byte[] buffer = new byte[1024];
        DatagramPacket dtg = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(dtg);
        } catch (IOException ex) {
            Logger.getLogger(EchangeTFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (dtg.getPort()!=portUDP){
            portUDP=dtg.getPort();
        }
        return dtg.getData();
    }
    
    public boolean receiveAck(int n){
        byte[] buffer = new byte[1024];
        buffer=receivePacket();
        return PacketAck.isNAckPacket(buffer,n);
    }
    
    public void sendAck(int n){
        PacketAck ack = new PacketAck(n);
        sendPacket(ack);
    }
    
    public boolean trySendPacket (PacketTFTP packet, int n){
         for (int i=0; i<NB_TENTATIVE;i++){
           sendPacket(packet);
           if (receiveAck(n)){
               return true;
           }
        }
        return false;
    }
    
    public boolean tryReceiveDataPacket (PacketData packet, int n){
        byte[] buffer = new byte[1024]; 
        for (int i=0; i<NB_TENTATIVE;i++){
           buffer=receivePacket();
           if (PacketData.isNDataPacket(buffer,n)){
               sendAck(n);
               packet = new PacketData (n,buffer);
               return true;
           }
        }
        return false;
    }
    
    public FileInputStream openReadFile (File file){
        try {
            return new FileInputStream(file);
        }
        catch (FileNotFoundException ex) {
            //impossible d'ouvrir le fichier
        }
        return null;
    }
    
    public FileOutputStream openWriteFile (File file){
        try {
            return new FileOutputStream(file);
        }
        catch (FileNotFoundException ex) {
            //impossible d'ouvrir le fichier
        }
        return null;
    }
    
    public boolean closeReadFile (FileInputStream f){
        try {
            f.close();
            return true;
        } catch (IOException ex) {
            //impossible de fermer le fichier
            return false;
        }
    }
    
    public boolean closeWriteFile (FileOutputStream f){
        try {
            f.close();
            return true;
        } catch (IOException ex) {
            //impossible de fermer le fichier
            return false;
        }
    }
}
