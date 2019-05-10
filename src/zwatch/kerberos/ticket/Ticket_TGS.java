package zwatch.kerberos.ticket;

import com.google.gson.Gson;
import zwatch.kerberos.Utils;

import java.security.Key;
import java.util.Base64;

public class Ticket_TGS {
    byte[] IDc, ADc,IDtgs;
    long TS2, Lifetime1;
    Key key;
    public String Pack(){
        return Utils.gson.toJson(this, Ticket_TGS.class);
    }

    public static Ticket_TGS UnPack(String rowData){
        return Utils.gson.fromJson(rowData, Ticket_TGS.class);
    }

    public static Ticket_TGS UnCryptPack(String rowData, String pass){
        byte[] row =Utils.base64de.decode(rowData);

        return Utils.gson.fromJson(rowData, Ticket_TGS.class);
    }


}



