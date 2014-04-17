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
    
    @Override
    public boolean isDatagramPacket(byte[] datagram) {
        return "02".equals(datagram.toString().substring(0,2));
    }
}
