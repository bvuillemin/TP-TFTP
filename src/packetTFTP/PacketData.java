/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package packetTFTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Dimitri
 */
public class PacketData extends PacketTFTP{

    private int block;
    private byte[] data;
    
    public PacketData() {
        super(3);
        this.data = new byte[512];
    }
    
    public PacketData(int _block,byte[] _data) {
        super(3);
        this.block = _block;
        this.data = _data;
        createDatagram();
    }
    
    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    
    public static boolean isDataPacket(byte[] datagram) {
        return 3==getOpcode(datagram) && datagram.toString().length()<=516;
    }
    
    public static boolean isNDataPacket(byte[] datagram, int number){
        if (isDataPacket(datagram)){
            int value = datagram[2] & 0xff;
            if(value==number){
                return false;
            }
        }
        return false;
    }
    
    @Override
    public boolean getDatagramPacket(byte[] _data) {
        if (this.isDataPacket(_data)){
            datagram=_data;
            opcode=3;
            int value =0;
            value = value << 8;
            value += datagram[2];
            block=value;
            
            vz
            /*A supprimer*/
            try {
                String str = new String(_data, "UTF-8");
                System.out.println(str);
            } catch (UnsupportedEncodingException ex) {
            }
            
            data=new byte[datagram.length-4];
            System.arraycopy(datagram, 4, data, 0, datagram.length-4);
            return true;
        }
        else return false;
    }

    @Override
    public void buildDataByte() {
        try {
            byte[] blockByte = intToByte(block);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(blockByte);
            outputStream.write(data);
            dataByte = outputStream.toByteArray();
        }
        catch(IOException ex){
            System.out.println("Impossible de convertir la requête en byte[] : "+ex);
        }
    }    

    @Override
    public void afficherPacket() {
        System.out.println("Opcode : "+opcode+"    Block : " + block + "   Data : "+data.toString()+ "    Datagram : "+datagram.toString());
    }
}
