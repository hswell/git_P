package zwatch.kerberos.AS;

import com.sun.javafx.binding.StringFormatter;
import zwatch.kerberos.ticket.Ticket_TGS;
import zwatch.kerberos.ticket.Ticket_V;
import zwatch.kerberos.packet.AS2Client;
import zwatch.kerberos.packet.Client2AS;
import zwatch.kerberos.packet.packetTool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AS_Server extends Thread implements IAS_Server {
    Logger log = Logger.getLogger("Server.log");
    int port = 9999;
    ServerSocket server = null;

    StringFormatter stringFormatter;

    @Override
    public void run() {
        super.run();
        boolean hasError=false;

        log.log(Level.INFO,"服务器启动");
        try {
            server = new ServerSocket(port);
        }catch (IOException e) {
            log.log(Level.SEVERE, e.getLocalizedMessage());
            hasError=true;
        }
        while(!hasError){
            try {
                Socket socket = server.accept();
                AS_Server_n a=new AS_Server_n();
                log.log(Level.INFO, "来自 "+socket.getInetAddress().getHostName()+":"+socket.getPort()+" 连接到服务器");
                a.socket=socket;
                a.start();
            }catch (IOException e) {
                e.printStackTrace();
                hasError=true;
            }
        }
        log.log(Level.INFO,"服务器关闭");
    }

    @Override
    public void Login(String uid) {

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

class AS_Server_n extends Thread implements IAS_Server {
    Socket socket = null;
    private static Logger logger=Logger.getLogger("AS_Server_n.log");
    private static String password="20161001";
    @Override
    public void run() {
        Reader reader = null;
        Writer writer = null;
        try {
            reader = new InputStreamReader(socket.getInputStream());
            writer = new OutputStreamWriter(socket.getOutputStream());

            String clientRowData=packetTool.FromReader(reader);
            logger.log(Level.INFO , "server recv rowdata: " + clientRowData);

            Client2AS cAs=Client2AS.unpack(clientRowData);
            String pass=getPassword(cAs.Uid);
            logger.log(Level.INFO , "the user: "+cAs.Uid+" request ticket");

            AS2Client as2Client=new AS2Client();
            as2Client.Uid=cAs.Uid;
            Ticket_TGS ticket_tgs=new Ticket_TGS();
            String sendPack=as2Client.CryptPack(pass);
            writer.write(sendPack);
            writer.flush();
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }

    @Override
    public void Login(String uid) {

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

    private String getPassword(String uid){
        return password;
    };
}