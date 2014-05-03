/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package packetTFTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author Dimitri
 */
public class PacketError extends PacketTFTP{
    
    private int errorCode;
    private String errMsg;
    
    public PacketError() {
        super(5);
    }
    
    public PacketError(int _errorCode,String _errMsg) {
        super(5);
        this.errorCode = _errorCode;
        this.errMsg = _errMsg;
        createDatagram();
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
       try {
            byte[] msgByte = errMsg.getBytes("ascii");
            byte[] codeByte = intToByte(errorCode);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(msgByte);
            outputStream.write(codeByte);
            dataByte = outputStream.toByteArray();
        }
        catch(IOException ex){
            System.out.println("Impossible de convertir le packet erreur en byte[] : " + ex);
        }
    }
    
}
