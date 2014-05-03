/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tptftp;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import packetTFTP.*;

/**
 *
 * @author Dimitri
 */
public class EnvoiTFTP extends EchangeTFTP {
    
    private File file;
    final int NB_TENTATIVE=3;
    
    public EnvoiTFTP (InetAddress _ip, File _file){
        super();
        this.portUDP=69;
        this.adresseIP=_ip;
        this.file=_file;
    }
    
    public void sendWRQ (){
        PacketWRQ wrq = new PacketWRQ ("netascii",file.getName());
        try {
            sendPacket(wrq.getDatagram());
        } 
        catch (IOException ex) {
            Logger.getLogger(EnvoiTFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean receiveAckWRQ(){
        byte[] buffer = new byte[1024];
        buffer=receivePacket();
        return false;
    }
    
    public void sendDataPacket(int block,byte[] data){
        PacketData packet= new PacketData (block, data);
        try {
            sendPacket(packet.getDatagram());
        } 
        catch (IOException ex) {
            Logger.getLogger(EnvoiTFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean tryWRQ (){
        for (int i=0; i<NB_TENTATIVE;i++){
           sendWRQ();
           if (receiveAckWRQ()){
               return true;
           }
        }
        return false;
    }
    
    public void trySendData (){
        for (int i=0; i<NB_TENTATIVE;i++){
           sendWRQ();
        } 
    }
    
    public void sendData (){
        byte[] buffer=new byte[512]; 
    }
    
    public int SendFile(){
        if (tryWRQ()){
            
        }
        else{
            
        }
        return 0;
    }
    
    @Override
    public void run() {
        SendFile();
    }
}
