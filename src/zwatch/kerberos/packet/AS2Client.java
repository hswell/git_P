package zwatch.kerberos.packet;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import zwatch.kerberos.Utils;
import zwatch.kerberos.crypt.DES;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Logger;
/*
    Realm_c||IDc||Ticket_tgs||E(Kc, [Kc_tgs||Times||Nonce1||Realm_tgs||IDtgs])
    Ticket_tgs= E(Ktgs,[Kc,tgs|| IDc|| ADc|| IDtgs|| TS2|| Lifetime2])
 */


public class AS2Client {
    private static Logger logger = Logger.getLogger("pack.log");


    //E(Kc,[Kc,tgs|| IDtgs|| TS2|| Lifetime2|| Tickettgs])
    public byte[] Kc_tgs, IDtgs;
    public byte[] Ticket_tgs;
    public long TS2, Lifetime2;

    public AS2Client(byte[] Kc_tgs, byte[] IDtgs, byte[] Ticket_tgs, long TS2, long Lifetime2) {
        this.Kc_tgs = Kc_tgs;
        this.IDtgs = IDtgs;
        this.TS2 = TS2;
        this.Lifetime2 = Lifetime2;
        this.Ticket_tgs = Ticket_tgs;
    }
    public AS2Client(byte[] Kc_tgs, byte[] IDtgs, byte[] Ticket_tgs, long TS2){
        this(Kc_tgs, IDtgs, Ticket_tgs, TS2, Utils.Default_Lifetime);
    }

    public String cryptPack(byte[] pass) throws Exception {
        String ret1=pack();
        return Utils.encrypt_des(ret1, pass);
    };

    public String pack(){
        return Utils.gson.toJson(this, AS2Client.class);
    };

    public static AS2Client unPack(String rowData){
        return Utils.gson.fromJson(rowData, AS2Client.class);
    };

    public static AS2Client unCryptPack(String rowData, byte[] pass) throws Exception {
        rowData=Utils.decrypt_des(rowData, pass);
        return unPack(rowData);
    }

}
