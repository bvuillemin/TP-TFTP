/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tptftp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import packetTFTP.PacketTFTP;

/**
 *
 * @author p1105476
 */
public class Machine {
    private InetAddress adresseIP;
    private int portUDP;
    private byte[] buffer;
    private DatagramSocket socket;
    private InetAddress receptionIP;
    private String receptionData;
    private int receptionPort;

    public String getReceptionData() {
        return receptionData;
    }

    public void setReceptionData(String receptionData) {
        this.receptionData = receptionData;
    }
    
    public InetAddress getReceptionIP() {
        return receptionIP;
    }

    public void setReceptionIP(InetAddress receptionIP) {
        this.receptionIP = receptionIP;
    }

    public int getReceptionPort() {
        return receptionPort;
    }

    public void setReceptionPort(int receptionPort) {
        this.receptionPort = receptionPort;
    }

    public InetAddress getAdresseIP() {
        return adresseIP;
    }

    public void setAdresseIP(InetAddress adresseIP) {
        this.adresseIP = adresseIP;
    }

    public int getPortUDP() {
        return portUDP;
    }

    public void setPortUDP(int portUDP) {
        this.portUDP = portUDP;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
    
    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }
    
    public void initSocket (){
        
        try{
            socket = new DatagramSocket (portUDP, adresseIP);
            socket.setSoTimeout(20000);
        }
        catch (SocketException ex) {
            System.err.println("Port déjà occupé");
        }
    } 
    
    public void sendMessage (InetAddress ip,int port, byte[] data){
        DatagramPacket dp = new DatagramPacket (data, data.length,ip, port);
        
        try{
            this.getSocket().send(dp);
        }
        catch (IOException ex) {
            System.err.println("Impossible d'envoyer le message UDP");
        }
    }
    
    public void envoiMessage (InetAddress ip,int port,String realData){
        byte[] data;
        try{
            data= realData.getBytes("ascii");
            this.sendMessage(ip, port, data);
        }
        catch (UnsupportedEncodingException ex) {
            System.err.print("Impossible d'envoyer le message UDP");
        }
    }
    
    public DatagramPacket recieveMessage (){
        DatagramPacket dp = new DatagramPacket (buffer, buffer.length);
        
        try{
            this.getSocket().receive(dp);
        }
        catch (Exception exc) {
            if (exc instanceof IOException){
                System.err.println("Impossible de recevoir le message UDP");
                return null;
            }
            else if (exc instanceof SocketTimeoutException){
                System.err.println("Timeout.");
                return null;
            }
        }
        return dp;
    }
    
    public boolean sauvegarderMessage(DatagramPacket dp,PacketTFTP packet){
        receptionIP=dp.getAddress();
        receptionPort=dp.getPort();
        try{
            receptionData=new String(dp.getData(),"ascii");
            return packet.getDatagramPacket(dp.getData());
        }
        catch(UnsupportedEncodingException ex){
            System.err.println("Impossible de sauvegarder le message UDP");
        }
        return false;
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
    
    public Machine (InetAddress IP){
        adresseIP=IP;
        portUDP = scanPorts(1024,1500);
        receptionPort = 0;
        buffer=new byte[512];
        initSocket ();
    }
}

