package zwatch.kerberos.TGS;


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

    //StringFormatter stringFormatter;

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

    @Override
    public void run() {
        super.run();
        Reader reader = null;
        Writer writer = null;
        try {
            reader = new InputStreamReader(socket.getInputStream());
            writer = new OutputStreamWriter(socket.getOutputStream());

            String clientRowData= packetTool.FromReader(reader);
            logger.log(Level.INFO , "server recv rowdata: " + clientRowData);
            Client2TGS cTGS = Client2TGS.unpack(clientRowData);
            if(Verification(cTGS)){

            }else{
                logger.log(Level.INFO , "Verification error: " + clientRowData);
                throw new IOException("Verification error");
            }
            String pass = getPassword(new Ticket_TGS());
            TGS2Client tgs2Client=new TGS2Client();

            String sendPack=tgs2Client.CryptPack(pass);
            writer.write(sendPack);
            writer.flush();

            reader.close();
            writer.close();
            socket.close();

        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }


    }

    String getPassword(Ticket_TGS ticket_tgs){
        return "20161001742";
    }

    boolean Verification(Client2TGS client2TGS){
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