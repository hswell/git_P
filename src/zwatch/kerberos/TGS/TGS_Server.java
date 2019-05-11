package zwatch.kerberos.TGS;


import zwatch.kerberos.Utils;
import zwatch.kerberos.ticket.Authenticator_tgs;
import zwatch.kerberos.ticket.Ticket_V;
import zwatch.kerberos.ticket.Ticket_TGS;
import zwatch.kerberos.packet.TGS2Client;
import zwatch.kerberos.packet.Client2TGS;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TGS_Server extends Thread implements ITGS_Server {

    Logger log = Logger.getLogger("TGS_Server.log");
    int port = 9998;
    ServerSocket server = null;

    @Override
    public void run() {
        super.run();
        boolean hasError=false;

        log.log(Level.INFO,"票据服务器启动");
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getLocalizedMessage());
            hasError=true;
        }
        while(!hasError) {
            try {
                Socket socket = server.accept();
                TGS_Server_n a = new TGS_Server_n();
                log.log(Level.INFO, "来自 " + socket.getInetAddress().getHostName() + ":" + socket.getPort() + " 连接到服务器");
                a.socket = socket;
                a.start();
            } catch (IOException e) {
                e.printStackTrace();
                hasError = true;
            }
        }
        log.log(Level.INFO,"票据服务器关闭");
    }


    @Override
    public void Login(Ticket_TGS ticket_as) {

    }

    @Override
    public Ticket_V getTicket(String pass) {

        return null;
    }

    @Override
    public void SaveConfig(String filename) {

    }

    @Override
    public void LoadConfig(String filename) {

    }
}


class TGS_Server_n extends Thread implements ITGS_Server{
    Socket socket = null;
    private static Logger logger=Logger.getLogger("AS_Server_n.log");
    private static String password="20161001";
    private static String v_password="20161001";

    @Override
    public void run() {
        super.run();
        Reader reader = null;
        Writer writer = null;
        try {
            reader = new InputStreamReader(socket.getInputStream());
            writer = new OutputStreamWriter(socket.getOutputStream());

            String C_TGS_RowData= Utils.FromReader(reader);
            logger.log(Level.INFO , "server recv rowdata: " + C_TGS_RowData);
            Client2TGS cTGS = Client2TGS.unPack(C_TGS_RowData);

            Ticket_TGS ticket_tgs=Ticket_TGS.UnCryptPack(cTGS.Ticket_tgs, password);
            Authenticator_tgs auth=Authenticator_tgs.unCryptPack(cTGS.Authenticator_tgs, ticket_tgs.Kc_tgs);
            if(Verification(ticket_tgs, auth)){
                byte[] Kc_v=Utils.RandomDesKey();
                long TS=Utils.TimeStamp();
                byte[] ADc= socket.getInetAddress().toString().getBytes();;
                Ticket_V ticket_v = new Ticket_V(Kc_v, ticket_tgs.IDc,ADc ,cTGS.IDv,TS);
                TGS2Client tgs2Client = new TGS2Client(Kc_v, cTGS.IDv, ticket_v.CryptPack(v_password),TS);
                String sendPack=tgs2Client.cryptPack(ticket_tgs.Kc_tgs);
                writer.write(sendPack);
                writer.flush();
            }else{
                logger.log(Level.INFO , "Verification error: " + C_TGS_RowData);
                throw new IOException("Verification error");
            }

            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }

    String getPassword(Ticket_TGS ticket_tgs){
        return "20161001742";
    }

    boolean Verification(Ticket_TGS ticket_tgs, Authenticator_tgs auth){
        return true;
    }

    @Override
    public Ticket_V getTicket(String pass) {

        return null;
    }


    @Override
    public void Login(Ticket_TGS ticket_as) {

    }


    @Override
    public void SaveConfig(String filename) {

    }

    @Override
    public void LoadConfig(String filename) {

    }
}