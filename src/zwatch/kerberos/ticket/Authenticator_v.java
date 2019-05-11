package zwatch.kerberos.ticket;
import zwatch.kerberos.Utils;

public class Authenticator_v {
    //Authenticatorc = E(Kc_v,[IDc||ADc||TS5])
    public byte[] IDc, ADc;
    public long TS5;

    public Authenticator_v(byte[] IDc, byte[] ADc, long TS5){
        this.ADc=ADc;
        this.IDc= IDc;
        this.TS5=TS5;
    }

    public  static Authenticator_v UnCryptPack(String row, String key) throws Exception {
        //TODO wait DES
        String ret=Utils.decrypt_des(row ,key);
        return unPack(ret);
    }

    public static Authenticator_v unPack(String row){
        return Utils.gson.fromJson(row, Authenticator_v.class);
    }

    public String pack(){
        return Utils.gson.toJson(this, Authenticator_v.class);
    };

    public String cryptPack(String pass) throws Exception {
        String ret=pack();
        ret=Utils.encrypt_des(ret ,pass);
        return ret;
    };



}
