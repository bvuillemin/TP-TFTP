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
public class PacketRRQ extends PacketRequest{
    
    public PacketRRQ() {
        super(1);
    }
    
    public PacketRRQ(String _mode, String _fileName) {
        super(1,_mode,_fileName);
    }
    
    public static boolean isRRQPacket(byte[] datagram) {
        return 1==getOpcode(datagram);
    }
    
    @Override
    public boolean getDatagramPacket(byte[] _data) {
        if (this.isRRQPacket(_data)){
            getRequestPacket(_data);
            return true;
        }
        else return false;
    }
}
