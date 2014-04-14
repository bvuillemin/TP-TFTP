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
public class PacketAck extends PacketTFTP{
    
    private int block;
    
    public PacketAck() {
        super("04");
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }
    
    @Override
    public boolean isDatagramPacket(byte[] datagram) {
        return "04".equals(datagram.toString().substring(0,2));
    }

    @Override
    public boolean getDatagramPacket(byte[] _data) {
        String _datagram;
        int i;
        if (this.isDatagramPacket(_data)){
            datagram=_data;
            _datagram=datagram.toString();
            block =Integer.parseInt(_datagram.substring(1,2));
            return true;
        }
        else return false;
    }

    @Override
    public void buildDataStr() {
        dataStr=Integer.toString(block);
    }
    
}
