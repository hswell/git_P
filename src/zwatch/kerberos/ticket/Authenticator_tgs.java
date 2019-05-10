package zwatch.kerberos.ticket;

import zwatch.kerberos.Utils;

public class Authenticator_tgs {
    //Authenticatorc = E(Kc_tgs,[IDc||ADc||TS3])
    byte[] IDc, ADc;
    long TS3;

    public Authenticator_tgs(byte[] IDc, byte[] ADc, long TS3){
        this.ADc=ADc;
        this.IDc=IDc;
        this.TS3=TS3;
    }

    public String CryptPack(){
        //TODO wait for DES
        return null;
    };

    public static Authenticator_tgs UnPack(String rowData, String Kc_tgs){
        return Utils.gson.fromJson(rowData, Authenticator_tgs.class);
    }
}
