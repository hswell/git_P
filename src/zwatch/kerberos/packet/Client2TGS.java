package zwatch.kerberos.packet;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

public class Client2TGS {
    public String UID="";
    public String VID="";
    public String RowTicket_tgs="";
    public String Auth="";

    static Gson gson = new Gson();
    public String pack(){
        return gson.toJson(this, Client2AS.class);
    };

    public static Client2TGS unpack(String rowData){
        JsonReader reader=new JsonReader(new StringReader(rowData));
        return gson.fromJson(reader, Client2TGS.class);
    };
}
