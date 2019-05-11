package zwatch.kerberos.Client;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import zwatch.kerberos.IServerConfig;
import zwatch.kerberos.Utils;
import zwatch.kerberos.packet.AS2Client;
import zwatch.kerberos.packet.Client2AS;
import zwatch.kerberos.packet.Client2TGS;
import zwatch.kerberos.packet.TGS2Client;
import zwatch.kerberos.ticket.Authenticator_tgs;
import zwatch.kerberos.ticket.Authenticator_v;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TGS_proxy implements IServerConfig {
        Socket client;
        String rowData=null;
        String Kc_tgs;
        public TGS2Client tgs2Client=null;
        static String host = "127.0.0.1";
        static int port = 9998;
        static Logger logger=Logger.getLogger("TgsServer.log");

    public void run() {
        LoadConfig("TGS_proxy.config");
        try {
            client = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Login(AS2Client as2Client) {

        if(client == null){
            run();
        }

        Writer writer = null;
        Reader reader=null;
        byte[] IDv="01".getBytes();
        byte[] IDc="20161001742".getBytes();
        long TS5=Utils.TimeStamp();
        String ticket=new String(as2Client.Ticket_tgs);
        Kc_tgs=new String(as2Client.Kc_tgs);
        byte[] ADc=client.getLocalAddress().toString().getBytes();
        Authenticator_tgs authenticator_tgs=new Authenticator_tgs(IDc, ADc, TS5);
        try {
            Client2TGS client2TGS=new Client2TGS(IDv,ticket,authenticator_tgs.CryptPack(Kc_tgs));
            assert client != null;
            writer = new OutputStreamWriter(client.getOutputStream());
            reader = new InputStreamReader(client.getInputStream());

            String send=client2TGS.pack();
            logger.log(Level.INFO , "client will send: "+send);
            writer.write(send.toCharArray());
            writer.flush();
            logger.log(Level.INFO , "client send over: ");
            //AS2Client as2Client=AS2Client.FromReader(reader);
            rowData=Utils.FromReader(reader);
            logger.log(Level.INFO , "client recv: "+rowData);
            tgs2Client=TGS2Client.UnCryptPack(rowData, Kc_tgs);

            logger.log(Level.INFO, "get Ticket of "+new String(tgs2Client.IDv));
            logger.log(Level.INFO, "LifeTime is "+tgs2Client.Lifetime3);
            reader.close();
            writer.close();
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getRowTicket(String pass) {
        return null;
    }

    @Override
    public void SaveConfig(String filename) {
        File file=new File(filename);
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
            FileWriter fileWriter=new FileWriter(file);
            JsonWriter jsonWriter=new JsonWriter(fileWriter);
            jsonWriter.beginObject();
            jsonWriter.name("ip").value(host);
            jsonWriter.name("port").value(port);
            jsonWriter.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
    }

    @Override
    public void LoadConfig(String filename) {
        host = "127.0.0.1";
        port = 9998;

        try {
            File file=new File(filename);
            if(!file.exists()){
                file.createNewFile();
                FileWriter fileWriter=new FileWriter(file);
                fileWriter.write("{\"port\":9998, \"ip\":\"172.0.0.1\"}");
                fileWriter.close();
                return ;
            }
            FileReader fileReader=null;
            try{
                fileReader=new FileReader(filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
            JsonReader jsonReader=new JsonReader(fileReader);
            jsonReader.beginObject();
            while (jsonReader.hasNext()){
                switch (jsonReader.nextName()){
                    case "ip":{
                        host=jsonReader.nextString();
                        break;
                    }
                    case "port":{
                        port=jsonReader.nextInt();
                        break;
                    }
                }
            }
            jsonReader.endObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
