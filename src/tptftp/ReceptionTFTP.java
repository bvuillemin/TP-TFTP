/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tptftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import packetTFTP.*;

/**
 *
 * @author Dimitri
 */
public class ReceptionTFTP extends EchangeTFTP {
    private File file;
    
    public ReceptionTFTP (InetAddress _ip, File _file, int _port){
        super();
        this.portUDP=_port;
        this.adresseIP=_ip;
        this.file=_file;
    }
    
    public void receiveData(){
        FileOutputStream f =openWriteFile(file);
        PacketData data;
        if (f!=null){
            int i=1;
            try {
                data=new PacketData(i, new byte[512]);
                while (tryReceiveDataPacket(data,i)){
                    f.write(data.getDataByte());
                    i++;
                }
            } catch (IOException ex) {
                //impossible d'écrire
            }
        }
        closeWriteFile(f);
    }
    
    public int ReceiveFile(){
        PacketRRQ packet = new PacketRRQ ("netascii",file.getName());
        if (trySendPacket(packet,0)){
            receiveData();
        }
        else{
            //server not reachable
        }
        return 0;
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
