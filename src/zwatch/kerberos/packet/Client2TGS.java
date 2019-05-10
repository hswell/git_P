package zwatch.kerberos.packet;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import zwatch.kerberos.Client.Client;
import zwatch.kerberos.Utils;
import zwatch.kerberos.ticket.Authenticator_tgs;
import zwatch.kerberos.ticket.Authenticator_v;

import java.io.StringReader;

public class Client2TGS {
    //IDV|| Tickettgs|| Authenticatorc
    public byte[] IDv;
    public String Ticket_tgs;
    public String Authenticator_tgs;

    public Client2TGS(byte[] IDv, String Ticket_tgs, String Authenticator_tgs){
        this.IDv=IDv;
        this.Ticket_tgs=Ticket_tgs;
        this.Authenticator_tgs=Authenticator_tgs;
    }

    public String pack(){
        return Utils.gson.toJson(this, Client2AS.class);
    };

    public static Client2TGS unPack(String rowData){
        return Utils.gson.fromJson(rowData, Client2TGS.class);
    };
}
