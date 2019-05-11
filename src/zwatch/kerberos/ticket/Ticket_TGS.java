package zwatch.kerberos.ticket;

import com.google.gson.Gson;
import zwatch.kerberos.Utils;

import java.security.Key;
import java.util.Base64;
//Tickettgs= E(Ktgs, [Kc,tgs|| IDC|| ADC|| IDtgs|| TS2|| Lifetime2])
public class Ticket_TGS {
    //byte[] IDc, ADc,IDtgs;
    byte[] IDc=null , ADc=null ,IDtgs=null ;
    long TS2, Lifetime2;
    String Kc_tgs="";

    static long Default_Lifetime=6000000;
    public Ticket_TGS(byte[] IDc, byte[] ADc, byte[] IDtgs, long TS2, long Lifetime2){
        this.IDc = IDc;
        this.ADc = ADc;
        this.IDtgs = IDtgs;
        this.TS2=TS2;
        this.Lifetime2=Lifetime2;
    }

    public Ticket_TGS(byte[] IDc, byte[] ADc, byte[] IDtgs, long TS2){
        this(IDc, ADc, IDtgs, TS2, Default_Lifetime);
    }

    public String Pack(){
        return Utils.gson.toJson(this, Ticket_TGS.class);
    }

    public static Ticket_TGS UnPack(String rowData){
        return Utils.gson.fromJson(rowData, Ticket_TGS.class);
    }

    public static Ticket_TGS UnCryptPack(String rowData, String pass){
        byte[] row =Utils.base64de.decode(rowData);
        //
        return UnPack(new String(row));
    }


}



