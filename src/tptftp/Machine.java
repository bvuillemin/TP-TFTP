/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sockets_udp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

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
    private int receptionPort;

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
            // suite du programme
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
    
    public void sauvegarderMessage(DatagramPacket dp) throws UnsupportedEncodingException{
        receptionIP=dp.getAddress();
        receptionPort=dp.getPort();
        receptionData=new String(dp.getData(),"ascii");
    }

    static void scanPorts(int debut, int nb) {
        DatagramSocket port;
        for (int i = debut; i <= debut+nb; i++) {
            try {
                port = new DatagramSocket(i);
                System.out.println((i)+" : Disponible");
            } 
            catch (SocketException e) {
                System.out.println((i)+" : Utilisé");
            }
        }
    }
    
    public Machine (InetAddress IP, int port){
        adresseIP=IP;
        portUDP = port;
        receptionPort = 0;
        buffer=new byte[100];
        initSocket ();
    }
}

