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
public class PacketError extends PacketTFTP{
    
    private int errorCode;
    private String errMsg;
    
    public PacketError() {
        super("05");
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String ErrMsg) {
        this.errMsg = ErrMsg;
    }
    
    @Override
    public boolean isDatagramPacket(byte[] datagram) {
        return "05".equals(datagram.toString().substring(0,2));
    }

    @Override
    public boolean getDatagramPacket(byte[] _data) {
        String _datagram;
        int i;
        if (this.isDatagramPacket(_data)){
            datagram=_data;
            _datagram=datagram.toString();
            _datagram=_datagram.substring(0,_datagram.length()-1);
            errorCode =Integer.parseInt(_datagram.substring(1,2));
            errMsg=_datagram.substring(2,_datagram.length());
            return true;
        }
        else return false;
    }

    @Override
    public void buildDataByte() {
       String dataStr=errorCode+errMsg;
       try {
            dataByte=dataStr.getBytes("ascii");
        }
        catch(Exception ex){
            System.out.println("Impossible de convertir le packet erreur en byte[]");
        }
    }
    
}
