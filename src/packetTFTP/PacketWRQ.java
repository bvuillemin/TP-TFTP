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
public class PacketWRQ extends PacketRequest {
    
    public PacketWRQ() {
        super(2);
    }
    
    public PacketWRQ(String _mode, String _fileName) {
        super(2,_mode,_fileName);
    }
    
    public static boolean isWRQPacket(byte[] datagram) {
        return 2==getOpcode(datagram);
    }

    @Override
    public boolean getDatagramPacket(byte[] _data) {
        if (this.isWRQPacket(_data)){
            getRequestPacket(_data);
            return true;
        }
        else return false;
    }
}
