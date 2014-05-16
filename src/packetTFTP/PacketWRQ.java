package packetTFTP;

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
    public boolean getDatagramPacket(byte[] _data){
        if (this.isWRQPacket(_data)){
            opcode=2;
            getRequestPacket(_data);
            return true;
        }
        else return false;
    }
}
