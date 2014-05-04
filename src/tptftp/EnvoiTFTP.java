/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tptftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    
    public EnvoiTFTP (InetAddress _ip, File _file){
        super();
        this.portUDP=69;
        this.adresseIP=_ip;
        this.file=_file;
    }
    
    public void sendData (){
        FileInputStream f=openFile(file);
        PacketData data;
        if (f!=null){
            byte[] buffer=new byte[512];
            int i=1;
            try {
                while (f.read(buffer)!=1){
                    data=new PacketData(i, buffer);
                    trySendPacket(data,i);
                }
                int taille=f.available();
                if (taille>0){
                    buffer=new byte[taille];
                    f.read(buffer);
                    data=new PacketData(i, buffer);
                    trySendPacket(data,i);
                }
            } catch (IOException ex) {
                //impossible de lire
            }
        }
        closeFile(f);
    }
    
    public int SendFile(){
        PacketWRQ packet = new PacketWRQ ("netascii",file.getName());
        if (trySendPacket(packet,0)){
            sendData();
        }
        else{
            //server not reachable
        }
        return 0;
    }
    
    @Override
    public void run() {
        SendFile();
    }
}
