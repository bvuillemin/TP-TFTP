/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetTFTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

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

    public abstract boolean getDatagramPacket(byte[] _data);

    public abstract void buildDataByte();
}
