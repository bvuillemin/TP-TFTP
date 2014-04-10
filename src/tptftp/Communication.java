/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sockets_udp;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 *
 * @author Dimitri
 */
public class Communication extends Machine {
       
    public Communication (int portServeur, InetAddress ipServeur,int portClient, InetAddress ipClient){
        super (ipServeur, portServeur);
        setReceptionIP(ipServeur);
        setReceptionPort(portServeur);
    }
    
    public void run(){
        DatagramPacket dp= new DatagramPacket (getBuffer(), getBuffer().length);
        dp=recieveMessage();
        while(dp!=null){
            envoiMessage(getReceptionIP(), getReceptionPort(),getReceptionData());
            dp=recieveMessage();
        }
    }
}
