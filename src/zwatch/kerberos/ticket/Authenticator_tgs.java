package zwatch.kerberos.ticket;

import zwatch.kerberos.Utils;

import javax.rmi.CORBA.Util;
import java.math.BigInteger;

public class Authenticator_tgs {
    //Authenticatorc = E(Kc_tgs,[IDc||ADc||TS3])
    public byte[] IDc, ADc;
    public long TS3;

    public Authenticator_tgs(byte[] IDc, byte[] ADc, long TS3){
        this.ADc=ADc;
        this.IDc=IDc;
        this.TS3=TS3;
    }

    public String CryptPack(byte[] pass) throws Exception {
        String ret = pack();
        return Utils.encrypt_des(ret, pass);
    };

    public static Authenticator_tgs UnPack(String rowData){
        return Utils.gson.fromJson(rowData, Authenticator_tgs.class);
    }

    public static Authenticator_tgs unCryptPack(String rowData, byte[] Kc_tgs) throws Exception {
        rowData=Utils.decrypt_des(rowData, Kc_tgs);
        return UnPack(rowData);
    };

    public String pack(){
        return Utils.gson.toJson(this, Authenticator_tgs.class);
    }
}
