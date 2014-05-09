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
    
    public static boolean isErrorPacket(byte[] datagram) {
        return 5==getOpcode(datagram);
    }

    public static boolean isNErrorPacket(byte[] datagram, int n) {
        return isErrorPacket(datagram);
    }
    
    @Override
    public boolean getDatagramPacket(byte[] _data) {
        byte[]msg=new byte[datagram.length-3];
        if (this.isErrorPacket(_data)){
            datagram=_data;
            opcode=5;
            errorCode =datagram[2] & 0xff;
            System.arraycopy(datagram, 3, msg, 0, datagram.length-3);
            errMsg=msg.toString();
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

    @Override
    public void afficherPacket() {
        System.out.println("Opcode : " + opcode + "    ErrCode : " + errorCode + "    ErrMsg : " + errMsg +"   Datagram : " + datagram.toString());
    }
}
