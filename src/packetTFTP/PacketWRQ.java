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
        super("2");
    }
    
    @Override
    public boolean isDatagramPacket(byte[] datagram) {
        return datagram.toString().charAt(0)=='2';
    }
}
