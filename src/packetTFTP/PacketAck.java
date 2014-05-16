package packetTFTP;

public class PacketAck extends PacketTFTP {

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

    public static boolean isAckPacket(byte[] datagram, int n) {
        if(4 == (getOpcode(datagram))){
            return byteToInt(datagram) == n;
        }
        return false;
    }
    
    public static boolean isAckPacket(byte[] datagram) {
        return (4 == getOpcode(datagram));
    }

    @Override
    public boolean getDatagramPacket(byte[] _data) {
        if (PacketAck.isAckPacket(_data)) {
            datagram = _data;
            opcode = 4;
            block=byteToInt(_data);
            return false;
        } else {
            return false;
        }
    }
    
    @Override
    public void buildDataByte() {
        dataByte = intToByte(block);
    }

}
