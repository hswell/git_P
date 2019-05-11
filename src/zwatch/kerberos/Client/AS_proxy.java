package zwatch.kerberos.Client;

import zwatch.kerberos.IServerConfig;
import zwatch.kerberos.Utils;
import zwatch.kerberos.packet.AS2Client;
import zwatch.kerberos.packet.Client2AS;

import javax.rmi.CORBA.Util;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AS_proxy implements IServerConfig {
    String rowData=null;
    String RowTicket=null;
    Socket client = null;
    AS2Client as2Client=null;
    Client2AS client2AS=null;

    static String host = "127.0.0.1";
    static int port = 9999;
    static Logger logger=Logger.getLogger("ClientLog.log");


    public void run() {
        try {
            client = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Login(String uid) {
        logger.log(Level.INFO , uid+" will login.");

        if(client == null){
            run();
        }

        Writer writer = null;
        byte[] IDc="20161001742".getBytes();
        byte[] IDtgs="01".getBytes();
        client2AS=new Client2AS(IDc,IDtgs, Utils.TimeStamp());

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
            rowData=Utils.FromReader(reader);
            logger.log(Level.INFO , "client recv: "+rowData);

            reader.close();
            writer.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public String getRowTicket(String pass) throws Exception {
        if(RowTicket != null){
            return RowTicket;
        }else{
            if(as2Client == null){
                as2Client=AS2Client.unCryptPack(rowData,"20161001");
                logger.log(Level.INFO, as2Client.pack());
                System.out.println("完整包："+rowData);
                System.out.println("Tgs id"+as2Client.IDtgs);
                System.out.println("返回的时间戳"+as2Client.TS2);
            }
            RowTicket = new String(as2Client.Ticket_tgs);
            return RowTicket;
        }
    }

    @Override
    public void SaveConfig(String filename) {

    }

    @Override
    public void LoadConfig(String filename) {

    }



}
