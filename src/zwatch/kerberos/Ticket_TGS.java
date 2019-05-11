package zwatch.kerberos;

import com.google.gson.Gson;

public class Ticket_TGS {
    static Gson gson=new Gson();

    public String pack(){
        return gson.toJson(this, Ticket_TGS.class);
    }

    public static Ticket_TGS UnPack(String rowData, String pass){
        return gson.fromJson(rowData, Ticket_TGS.class);
    }
}



