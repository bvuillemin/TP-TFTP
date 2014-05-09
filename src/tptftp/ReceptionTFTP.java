/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tptftp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import packetTFTP.*;

/**
 *
 * @author Dimitri
 */
public class ReceptionTFTP extends EchangeTFTP {

    private String fileName;
    private final String PATH = "C:\\Users\\Pierre\\Documents\\";

    public ReceptionTFTP(InetAddress _ip, String _file) {
        super();
        this.portUDP = 69;
        this.adresseIP = _ip;
        this.fileName = _file;
    }

    public boolean tryReceiveDataPacket(PacketData packet) {
        byte[] buffer;
        
        for (int i = 0; i < NB_TENTATIVE; i++) {
            buffer = receiveDataPacket();

            if (packet.getDatagramPacket(buffer) || PacketError.isErrorPacket(buffer)) {
                if (!PacketError.isErrorPacket(buffer)) {
                    sendAck(packet.getBlock());
                    return true;
                }
            }
        }
        return false;
    }

    public void receiveData(PacketData data) {
        data.afficherPacket();
        FileOutputStream f = openWriteFile(PATH + fileName);
        if (f != null) {
            try {
                f.write(data.getData());
                while (data.getData().length >= 516 && tryReceiveDataPacket(data)) {
                    f.write(data.getData());
                }
            } catch (IOException ex) {
                System.out.println("Impossible d'écrire dans le fichier " + fileName);
            }
        }
        closeWriteFile(f);
    }

    public boolean trySendRRQ(PacketRRQ packet, PacketData data) {
        for (int i = 0; i < NB_TENTATIVE; i++) {
            sendPacket(packet);
            if (tryReceiveDataPacket(data)) {
                return true;
            }
        }
        return false;
    }

    public int ReceiveFile() {
        PacketRRQ packet = new PacketRRQ("netascii", fileName);
        PacketData data = new PacketData();
        if (trySendRRQ(packet, data)) {
            receiveData(data);
        } else {
            System.out.println("Serveur inaccessible");
        }
        return 0;
    }

    @Override
    public void run() {
        ReceiveFile();
    }
}
