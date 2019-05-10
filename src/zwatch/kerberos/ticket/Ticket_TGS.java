package zwatch.kerberos.ticket;

import zwatch.kerberos.Utils;

//Tickettgs= E(Ktgs, [Kc,tgs|| IDC|| ADC|| IDtgs|| TS2|| Lifetime2])
public class Ticket_TGS {
    //byte[] IDc, ADc,IDtgs;
    public byte[] IDc=null , ADc=null ,IDtgs=null ;
    public long TS2, Lifetime2;
    public String Kc_tgs="";


    public Ticket_TGS(byte[] IDc, byte[] ADc, byte[] IDtgs, long TS2, long Lifetime2){
        this.IDc = IDc;
        this.ADc = ADc;
        this.IDtgs = IDtgs;
        this.TS2=TS2;
        this.Lifetime2=Lifetime2;
    }

    public Ticket_TGS(byte[] IDc, byte[] ADc, byte[] IDtgs, long TS2){
        this(IDc, ADc, IDtgs, TS2, Utils.Default_Lifetime);
    }

    public String pack(){
        return Utils.gson.toJson(this, Ticket_TGS.class);
    }
    public String cryptPack(){
        return pack();
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



