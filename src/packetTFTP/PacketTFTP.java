/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetTFTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dimitri
 */
public abstract class PacketTFTP {

    protected byte[] datagram;
    protected byte[] dataByte;
    protected int opcode;

    public PacketTFTP(int _opcode) {
        opcode = _opcode;
    }

    public PacketTFTP(byte[] dataStr, int opcode) {
        this.dataByte = dataStr;
        this.opcode = opcode;
        createDatagram();
    }

    public byte[] getDatagram() {
        return datagram;
    }

    public byte[] getDataByte() {
        return this.dataByte;
    }

    public int getOpcode() {
        return opcode;
    }

    protected void createDatagram() {
        buildDataByte();
        try {
            byte[] opcodeByte = intToByte(opcode);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(opcodeByte);
            outputStream.write(dataByte);
            datagram = outputStream.toByteArray();
        }
        catch(IOException ex){
            System.out.println("Impossible de créer le datagram tftp : "+ ex);
        }
        
    }
    
    public byte[] intToByte(int i) {
        ByteBuffer data = ByteBuffer.allocate(2);
        data.putShort((short)i);
        return data.array();
    }
    
    public static int getOpcode(byte[] _dtg) {
        int value = 0;
        for(int i=0; i<2; i++)
        {
            value = value << 8;
            value += _dtg[i] & 0xff;
        }
        return value;
    }
    
    public abstract boolean getDatagramPacket(byte[] _data);

    public abstract void buildDataByte();
    
    public void afficherPacket(){
        try {
            if(this.dataByte != null){
            String str = new String(this.dataByte, "US-ASCII");
            System.out.println(str + " = " + dataByte.toString());
            }
        } catch (UnsupportedEncodingException ex) {
        }
    }
}
