package zwatch.kerberos.Client;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
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
    public AS2Client as2Client=null;
    Client2AS client2AS=null;

    static String host = "127.0.0.1";
    static int port = 9999;
    static Logger logger=Logger.getLogger("ClientLog.log");

    public void run() {
        LoadConfig("AS_proxy.config");
        try {
            client = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Login(String uid) throws IOException {
        logger.log(Level.INFO , uid+" will login.");

        if(client == null){
            run();
        }

        Writer writer = null;
        byte[] IDc="20161001742".getBytes();
        byte[] IDtgs="01".getBytes();
        client2AS=new Client2AS(IDc,IDtgs, Utils.TimeStamp());


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
    }

    public String getRowTicket(String pass) throws Exception {
        if(RowTicket != null){
            return RowTicket;
        }else{
            if(as2Client == null){
                as2Client=AS2Client.unCryptPack(rowData,"20161001");
                logger.log(Level.INFO, as2Client.pack());
                System.out.println("完整包："+rowData);
                System.out.println("Tgs_id: "+new String(as2Client.IDtgs));
                System.out.println("返回的时间戳"+as2Client.TS2);
            }
            RowTicket = new String(as2Client.Ticket_tgs);
            return RowTicket;
        }
    }

    @Override
    public void SaveConfig(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            JsonWriter jsonWriter = new JsonWriter(fileWriter);
            jsonWriter.beginObject();
            jsonWriter.name("ip").value(host);
            jsonWriter.name("port").value(port);
            jsonWriter.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    @Override
    public void LoadConfig(String filename) {
        host = "127.0.0.1";
        port = 9999;

        try {
            File file=new File(filename);
            if(!file.exists()){
                file.createNewFile();
                FileWriter fileWriter=new FileWriter(file);
                fileWriter.write("{\"port\":9999, \"ip\":\"172.0.0.1\"}");
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
