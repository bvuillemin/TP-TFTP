/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package packetTFTP;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Dimitri
 */
public abstract class PacketTFTP {
    protected byte[] datagram;
    protected byte[] dataByte;
    protected byte[] opcode;
    
    public PacketTFTP (String _opcode){
        opcode=_opcode;
    }
    
    public PacketTFTP(byte[] dataStr, byte[] opcode)
    {
        this.dataByte = dataStr;
        this.opcode = opcode;
    }

    public byte[] getDatagram() {
        return datagram;
    }
       
    public byte[] getDataStr() {
        return this.dataByte;
    }

    public byte[] getOpcode() {
        return opcode;
    }
    
    public void createDatagram (){
        String _datagram;
        try{
            buildDataStr();
            _datagram=opcode+dataByte;
            datagram=_datagram.getBytes("ascii");
        }
        catch (UnsupportedEncodingException ex) {
            System.err.print("Impossible de créer le packet TFTP");
        }
        
    }
    
    public abstract boolean isDatagramPacket (byte[] datagram);
    
    public abstract boolean getDatagramPacket (byte[] _data);
    
    public abstract void buildDataStr();
}
