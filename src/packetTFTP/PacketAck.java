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

    public static boolean isAckPacket(byte[] datagram) {
        return 4 == (getOpcode(datagram));
    }

    public static boolean isNAckPacket(byte[] datagram, int number) {
        if (isAckPacket(datagram)) {
            int value = datagram[2] & 0xff;
            if (value == number) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getDatagramPacket(byte[] _data) {
        if (PacketAck.isAckPacket(_data)) {
            datagram = _data;
            opcode = 4;
            
            int a = _data[2];
            int b = _data[3];
            block = (a << 8) | (b);
            
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void buildDataByte() {
        dataByte = intToByte(block);
    }

}
