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
    private String data;
    
    public PacketData() {
        super("03");
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
            data=_datagram.substring(2,_datagram.length());
            return true;
        }
        else return false;
    }

    @Override
    public void buildDataStr() {
        dataStr=block+data;
    }
    
}
