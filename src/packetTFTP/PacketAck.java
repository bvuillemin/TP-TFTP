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
public class PacketAck extends PacketTFTP{
    
    private int block;
    
    public PacketAck() {
        super(4);
    }
    
    public PacketAck(int _block) {
        super(4);
        this.block = _block;
        createDatagram();
    }
    
    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }
    
    public static boolean isAckPacket(byte[] datagram) {
        return "04".equals(datagram.toString().substring(0,2));
    }
    
    public static boolean isNAckPacket(byte[] datagram, int number){
        if (isAckPacket(datagram)){
            String _datagram=datagram.toString();
            if(Integer.parseInt(_datagram.substring(1,2))==number){
                return false;
            }
        }
        return false;
    }
    
    @Override
    public boolean getDatagramPacket(byte[] _data) {
        String _datagram;
        int i;
        if (this.isAckPacket(_data)){
            datagram=_data;
            _datagram=datagram.toString();
            block =Integer.parseInt(_datagram.substring(1,2));
            return true;
        }
        else return false;
    }

    @Override
    public void buildDataByte() {
        dataByte = intToByte(block);
    }
    
}
