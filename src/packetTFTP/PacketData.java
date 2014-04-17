/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package packetTFTP;

/**
 *
 * @author Dimitri
 */
public class PacketData extends PacketTFTP{

    private int block;
    private byte[] data;
    
    public PacketData() {
        super("03");
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
    
    @Override
    public boolean isDatagramPacket(byte[] datagram) {
        return "03".equals(datagram.toString().substring(0,2)) && datagram.toString().length()<=516;
    }

    @Override
    public boolean getDatagramPacket(byte[] _data) {
        String _datagram;
        int i;
        if (this.isDatagramPacket(_data)){
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
            int length = Integer.toString(block).length();
            dataByte=Integer.toString(block).getBytes("ascii");
            System.arraycopy(data, 0, dataByte,length, data.length);
        }
        catch(Exception ex){
            System.out.println("Impossible de convertir la requête en byte[]");
        }
    }
    
}
