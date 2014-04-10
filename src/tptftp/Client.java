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

/**
 *
 * @author p1105476
 */
public class Client extends Machine {

    
    public Client (InetAddress IP){
        super (IP);
    }
    
    public String scanText () {
        BufferedReader entree = new BufferedReader( new InputStreamReader(System.in)); 
        try {
            return entree.readLine();
        }
        catch (IOException ex) {
            System.err.println("Impossible de lire la saisie");
        }
        return null;
    }
    
    public boolean receptionClient (DatagramPacket dp) throws UnsupportedEncodingException{
        byte[] buffer = new byte[100];
        dp=this.recieveMessage();
        if (dp==null) return false;
        //Sauvegarde des données
        this.sauvegarderMessage(dp);
        
        //Affichage
        System.out.println(this.getReceptionData());
        System.out.println( "IP : " + this.getReceptionIP().toString());
        System.out.println( "Port : " + this.getReceptionPort());
        return true;
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
        String realData = "hello seveur RX302";
        client.envoiMessage(ipServeur, portServeur, realData);
        
        //Reception 
        client.receptionClient(dp);
        
        //Envoi du message scanné
        realData=client.scanText();
        client.envoiMessage(ipServeur, portServeur, realData);
        
        //Reception 
        client.receptionClient(dp);
    }
}