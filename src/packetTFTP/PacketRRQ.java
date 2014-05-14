package packetTFTP;

public class PacketRRQ extends PacketRequest {

    public PacketRRQ() {
        super(1);
    }

    public PacketRRQ(String _mode, String _fileName) {
        super(1, _mode, _fileName);
    }

    public static boolean isRRQPacket(byte[] datagram) {
        return 1 == getOpcode(datagram);
    }

    @Override
    public void getDatagramPacket(byte[] _data) throws Exception {
        if (PacketRRQ.isRRQPacket(_data)) {
            opcode = 1;
            getRequestPacket(_data);
        } else {
            throw new Exception ("Ce n'est pas un packet RRQ");
        }
    }
}
