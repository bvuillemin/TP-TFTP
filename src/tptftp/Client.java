/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tptftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import packetTFTP.*;

/**
 *
 * @author p1105476
 */
public class Client extends Machine {

    
    public Client (InetAddress IP){
        super (IP);
    }
    
    public boolean receptionClient (DatagramPacket dp,PacketTFTP packet){
        dp=this.recieveMessage();
        if (dp==null) return false;

        return this.sauvegarderMessage(dp,packet);
    }
    
    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     * @throws java.io.UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
        InetAddress ip,ipServeur;
        int portServeur=69;
        
        ip= java.net.InetAddress.getLocalHost();
        ipServeur= java.net.InetAddress.getLocalHost();
        
        Client client =new Client (ip);
        DatagramPacket dp= new DatagramPacket (client.getBuffer(), client.getBuffer().length);
        
        //Envoi du premier message au serveur
        PacketRRQ rrq= new PacketRRQ("","ascii");
        rrq.createDatagram();
        client.sendMessage(ipServeur, portServeur, rrq.getDatagram());
        
        //Reception 
        PacketData receptionData = new PacketData();
        client.receptionClient(dp,receptionData);
        
        client.sendMessage(ipServeur, portServeur, receptionData.getDatagram());
        
        //Reception 
        client.receptionClient(dp);
    }
}