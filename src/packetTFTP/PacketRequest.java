package packetTFTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class PacketRequest extends PacketTFTP{
    
    protected String fileName;
    protected String mode;
    
    public PacketRequest(int _opcode) {
        super(_opcode);
        mode = "";
        fileName="";
    }
    
    public PacketRequest(int _opcode,String _mode, String _fileName) {
        super(_opcode);
        mode = _mode;
        fileName=_fileName;
        createDatagram();
    }
    public String getFileName(){
        return fileName;
    }

    public String getMode() {
        return mode;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void getRequestPacket(byte[] _data) {
        String _datagram;
        int i;
        datagram=_data;
        opcode= getOpcode(datagram);
        _datagram=datagram.toString();
        _datagram=_datagram.substring(0,_datagram.length()-1);

        i=_datagram.lastIndexOf('0');
        mode=_datagram.substring(i,_datagram.length());
        fileName=_datagram.substring(1,i);
    }
    
    @Override
    public void buildDataByte(){
        try {
            byte[] fileByte = fileName.getBytes("ascii");
            byte[] modeByte = mode.getBytes("ascii");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(fileByte);
            outputStream.write((byte)0);
            outputStream.write(modeByte);
            outputStream.write((byte)0);
            dataByte = outputStream.toByteArray();
        }
        catch(IOException ex){
            System.out.println("Impossible de convertir la requête en byte[] : " + ex);
        }
    }
}
