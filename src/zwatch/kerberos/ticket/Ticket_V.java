package zwatch.kerberos.ticket;

import com.sun.xml.internal.bind.v2.model.core.ID;
import zwatch.kerberos.Utils;

public class Ticket_V {
    //Ticketv= E(KV, [Kc,v||IDC||ADC|| IDv||TS4||Lifetime4])

    public byte[] Kc_v, IDc, ADc, IDv;
    public long TS4, Lifetime4;

    public Ticket_V(byte[] Kc_v, byte[] IDc, byte[] ADc, byte[] IDv, long TS4, long Lifetime4){
        this.Kc_v=Kc_v;
        this.ADc=ADc;
        this.IDc=IDc;
        this.IDv=IDv;
        this.TS4=TS4;
        this.Lifetime4=Lifetime4;
    }
    public Ticket_V(byte[] Kc_v, byte[] IDc, byte[] ADc, byte[] IDv, long TS4) {
        this(Kc_v, IDc, ADc, IDv, TS4, Utils.Default_Lifetime);
    }

    public String Pack(){
        return Utils.gson.toJson(this, Ticket_V.class);
    };

    public String CryptPack(String KV){
        return null;
    };

    public Ticket_V UnPack(String RowData){
        return Utils.gson.fromJson(RowData, Ticket_V.class);
    };

    public Ticket_V UnCryptPack(String RowData, String pass){
        //TODO
        return Utils.gson.fromJson(RowData, Ticket_V.class);
    };


}
