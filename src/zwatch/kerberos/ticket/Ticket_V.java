package zwatch.kerberos.ticket;

import zwatch.kerberos.Utils;

public class Ticket_V {
    String RawData;
    String uid;
    String ADc;
    String IDs;
    long TS4, Lifetime2;

    public Ticket_V(){

    }

    public String Pack(){
        return Utils.gson.toJson(this, Ticket_V.class);
    };

    public String CryptPack(String pass){
        return null;
    };

    public Ticket_V UnPack(String RowData){
        return Utils.gson.fromJson(RowData, Ticket_V.class);
    };

    public Ticket_V UnPack(String RowData, String pass){

        return Utils.gson.fromJson(RowData, Ticket_V.class);
    };


}
