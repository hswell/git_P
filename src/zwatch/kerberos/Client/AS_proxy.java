package zwatch.kerberos.Client;

import zwatch.kerberos.AS.IAS_Server;
import zwatch.kerberos.ticket.Ticket_V;
import zwatch.kerberos.packet.AS2Client;
import zwatch.kerberos.packet.Client2AS;
import zwatch.kerberos.packet.packetTool;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AS_proxy implements IAS_Server {
    String rowData=null;
    Ticket_V ticket_as=null;
    Socket client = null;
    AS2Client as2Client=null;
    Client2AS client2AS=null;

    static String host = "127.0.0.1";
    static int port = 9999;
    static Logger logger=Logger.getLogger("ClientLog.log");

    @Override
    public void run() {

        try {
            client = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Login(String uid) {
        logger.log(Level.INFO , uid+" will login.");

        if(client == null){
            run();
        }

        Writer writer = null;
        client2AS=new Client2AS();
        client2AS.Uid=uid;

        try {
            assert client != null;
            writer = new OutputStreamWriter(client.getOutputStream());
            Reader reader=new InputStreamReader(client.getInputStream());

            String send=client2AS.pack();
            logger.log(Level.INFO , "client will send: "+send);
            writer.write(send.toCharArray());
            writer.flush();
            logger.log(Level.INFO , "client send over: ");
            //AS2Client as2Client=AS2Client.FromReader(reader);
            rowData=packetTool.FromReader(reader);
            logger.log(Level.INFO , "client recv: "+rowData);

            reader.close();
            writer.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Ticket_V getTicket(String pass) {
        if(ticket_as != null){
            return ticket_as;
        }else{
            as2Client=AS2Client.unpack(rowData,"20161001");
            logger.log(Level.INFO, as2Client.pack());
            System.out.println("完整包："+rowData);
            System.out.println("用户id"+as2Client.Uid);
            System.out.println("返回的时间戳"+as2Client.TimeStamp);

            ticket_as =new Ticket_V();
            return ticket_as;
        }
    }

    @Override
    public void SaveConfig(String filename) {

    }

    @Override
    public void LoadConfig(String filename) {

    }



}
