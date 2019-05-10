package zwatch.kerberos.packet;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.lang.management.ThreadInfo;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.SimpleFormatter;


public class Client2AS {

    public String Uid="20161001742";
    public String TGSid="tgs0";
    public String TimeStamp = simpleFormatter.format(new Date());

    private static SimpleDateFormat simpleFormatter
            =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
    private static Gson gson=new Gson();

    public Client2AS(){ }

    public String pack(){
        Gson gson=new Gson();
        return gson.toJson(this, Client2AS.class);
    };

    public static Client2AS unpack(String rowData){
        JsonReader reader=new JsonReader(new StringReader(rowData));
        return gson.fromJson(reader, Client2AS.class);
    }

}
