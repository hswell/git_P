package zwatch.kerberos.ticket;

import zwatch.kerberos.Utils;

import javax.rmi.CORBA.Util;

//Tickettgs= E(Ktgs, [Kc,tgs|| IDC|| ADC|| IDtgs|| TS2|| Lifetime2])
public class Ticket_TGS {
    //byte[] IDc, ADc,IDtgs;
    public byte[] IDc=null , ADc=null ,IDtgs=null ;
    public long TS2, Lifetime2;
    public byte[] Kc_tgs;


    public Ticket_TGS(byte[] IDc, byte[] ADc, byte[] IDtgs, byte[] Kc_tgs,long TS2, long Lifetime2){
        this.IDc = IDc;
        this.ADc = ADc;
        this.IDtgs = IDtgs;
        this.TS2=TS2;
        this.Kc_tgs=Kc_tgs;
        this.Lifetime2=Lifetime2;
    }

    public Ticket_TGS(byte[] IDc, byte[] ADc, byte[] IDtgs, byte[] Kc_tgs,long TS2){
        this(IDc, ADc, IDtgs, Kc_tgs, TS2, Utils.Default_Lifetime);
    }

    public String pack(){
        return Utils.gson.toJson(this, Ticket_TGS.class);
    }
    public String cryptPack(byte[] pass) throws Exception {
        String ret=pack();
        ret= Utils.encrypt_des(ret, pass);
        return ret;
    }

    public static Ticket_TGS UnPack(String rowData){
        return Utils.gson.fromJson(rowData, Ticket_TGS.class);
    }

    public static Ticket_TGS UnCryptPack(String rowData, byte[] pass) throws Exception {
        String ret=Utils.decrypt_des(rowData, pass);
        return UnPack(ret);
    }
}



