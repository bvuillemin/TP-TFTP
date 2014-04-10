/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tptftp;

import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author p1105476
 */
public class Serveur extends Machine{

    public Serveur (InetAddress IP){
        super (IP);
    }
    
    public boolean receptionServeur (DatagramPacket dp) throws UnsupportedEncodingException{
        byte[] buffer = new byte[100];
        dp=this.recieveMessage();
        if (dp==null) return false;
        //Sauvegarde des données
        this.sauvegarderMessage (dp);
        
        //Affichage
        System.out.println("Nouveau Client : IP :" + this.getReceptionIP().toString());
        System.out.println("Port : " + this.getReceptionPort());
        System.out.println("Message : " + this.getReceptionData());
        return true;
    }
    
    public void demarerServeur() throws UnsupportedEncodingException {
        DatagramPacket dp= new DatagramPacket (getBuffer(), getBuffer().length);
        while(true) 
        {
            if (receptionServeur(dp)) {
                String realData = "Seveur RX302 ready";
                envoiMessage(getReceptionIP(), getReceptionPort(), realData);
                Communication com = new Communication(getPortUDP(), getAdresseIP(),getReceptionPort(), getReceptionIP()); 
                com.run();
            } 
        }    
    }
    
    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     * @throws java.io.UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {
        InetAddress ip;
        
        ip=java.net.InetAddress.getLocalHost();                
        
        Serveur serveur =new Serveur (ip);
        
        serveur.demarerServeur();
        /*DatagramPacket dp= new DatagramPacket (serveur.getBuffer(), serveur.getBuffer().length);
        
        // Reception
        serveur.receptionServeur(dp);
        
        //Envoi confirmation connexion
        String realData = "Seveur RX302 ready";
        serveur.envoiMessage(serveur.getReceptionIP(), serveur.getReceptionPort(), realData);
        
        // Reception
        serveur.receptionServeur(dp);
        
        
        //Envoi confirmation connexion
        serveur.envoiMessage(serveur.getReceptionIP(), serveur.getReceptionPort(), serveur.getReceptionData());*/
    }
    
}