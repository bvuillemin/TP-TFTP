/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tptftp;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import packetTFTP.PacketWRQ;

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
    
    public void tryWRQ (){
        for (int i=0; i<NB_TENTATIVE;i++){
            sendWRQ();
        }
    }
    
    public int SendFile(){
        tryWRQ();
        return 0;
    }
    
    @Override
    public void run() {
        SendFile();
    }
}
