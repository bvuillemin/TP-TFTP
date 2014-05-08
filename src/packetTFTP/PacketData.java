/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package packetTFTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author Dimitri
 */
public class PacketData extends PacketTFTP{

    private int block;
    private byte[] data;
    
    public PacketData() {
        super(3);
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
        String _datagram;
        int i;
        if (this.isDataPacket(_data)){
            datagram=_data;
            _datagram=datagram.toString();
            block =Integer.parseInt(_datagram.substring(1,2));
            try{
                data=_datagram.substring(2,_datagram.length()).getBytes("ascii");
            }
            catch(Exception ex)
            {
                System.out.println("Impossible de convertir le packet erreur en byte[]");
            }
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
    
}
