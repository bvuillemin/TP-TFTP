/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tptftp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Dimitri
 */
public abstract class EchangeTFTP implements Runnable {
    protected InetAddress adresseIP;
    protected int portUDP;
    protected DatagramSocket socket;
    
    public EchangeTFTP (){
        try{
            socket = new DatagramSocket ();
            socket.setSoTimeout(20000);
        }
        catch (SocketException ex) {
            System.err.println("Impossible de créer le socket");
        }
    }
    
    public void sendPacket (byte[] data) throws IOException{
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
        return buffer;
    }
}
