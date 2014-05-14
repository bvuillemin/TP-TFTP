package packetTFTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
//import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

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
    
    public static byte[] intToByte(int i) {
        ByteBuffer data = ByteBuffer.allocate(2);
        data.putShort((short)i);
        return data.array();
    }
    
    public static int byteToInt (byte b[]) {
        ByteBuffer _block = ByteBuffer.allocate(2);
        _block.put(b[2]);
        _block.put((int) 1, b[3]);
        return (int) _block.getShort(0);
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
    
    public abstract void getDatagramPacket(byte[] _data) throws Exception;

    public abstract void buildDataByte();
    
    /**
     * Permet l'affichage du texte du paquet
     */
    public void afficherPacket() {
        /*try {
            String str = new String(this.datagram, "US-ASCII");*/
            System.out.println(/*str + " = " + */Arrays.toString(datagram));
        /*} catch (UnsupportedEncodingException ex) {
        }*/
    }
}
