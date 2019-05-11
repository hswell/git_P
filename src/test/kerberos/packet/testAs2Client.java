package test.kerberos.packet;

import zwatch.kerberos.packet.AS2Client;

import java.io.StringWriter;

public class testAs2Client {
    public static void main(String argv[]){
        String password="20161001";
        AS2Client as2Client=new AS2Client();
        as2Client.Uid="helloIamLiHua";
        String noCryPacket=as2Client.pack();
        String packet= as2Client.CryptPack(password);
        System.out.println(noCryPacket);
        System.out.println(packet);
        AS2Client as2Client1=AS2Client.unpack(packet, password);
        System.out.println(as2Client1.pack());
    }
}
