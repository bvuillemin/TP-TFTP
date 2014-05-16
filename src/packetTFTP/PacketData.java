package packetTFTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
            return byteToInt(datagram)==number;
        }
        return false;
    }
    
    @Override
    public boolean getDatagramPacket(byte[] _data) {
        if (this.isDataPacket(_data)) {
            datagram = _data;
            opcode = 3;
            block=byteToInt(_data);
            data = new byte[datagram.length - 4];
            System.arraycopy(datagram, 4, data, 0, datagram.length - 4);
            return true;
        } else {
            return false;
        }
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
