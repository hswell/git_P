package zwatch.kerberos.Client;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import zwatch.kerberos.IServerConfig;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class TGS_proxy implements IServerConfig {
        Socket client;
        static String host = "127.0.0.1";
        static int port = 9998;

        static Logger logger=Logger.getLogger("ClientLog.log");

    public void run() {
        LoadConfig("TGS_proxy.config");
    }

    public void Login(String ticket) {

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
