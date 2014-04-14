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
        super("01");
    }
    
    public PacketRRQ(String _mode, String _fileName) {
        super("01",_mode,_fileName);
    }
    
    @Override
    public boolean isDatagramPacket(byte[] datagram) {
        return "01".equals(datagram.toString().substring(0,2));
    }
}
