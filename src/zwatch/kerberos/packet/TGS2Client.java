package zwatch.kerberos.packet;

import com.google.gson.Gson;
import com.sun.xml.internal.bind.v2.model.core.ID;
import zwatch.kerberos.Utils;
import zwatch.kerberos.crypt.DES;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TGS2Client {
    //E(Kc_tgs, [Kc_v || IDV || TS4 || Lifetime3|| Ticketv])
    public byte[] Kc_v, IDv;
    public String Ticket_v;
    public long TS4, Lifetime3;

    public TGS2Client(byte[] Kc_v, byte[] IDv, String Ticket_v, long TS4, long Lifetime3){
        this.Kc_v=Kc_v;
        this.IDv=IDv;
        this.Ticket_v=Ticket_v;
        this.TS4=TS4;
        this.Lifetime3=Lifetime3;
    }
    public TGS2Client(byte[] Kc_v, byte[] IDv, String Ticket_v, long TS4){
        this(Kc_v, IDv, Ticket_v, TS4, Utils.Default_Lifetime);
    }

    public String cryptPack(String pass){
        String row=pack();
        String ret=null;
        try {
            ret= DES.decrypt(row.getBytes(), pass).toString();
        } catch (InvalidKeyException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String pack(){
        return Utils.gson.toJson(this, TGS2Client.class);
    }

    public TGS2Client UnPack(String rowData){
        return Utils.gson.fromJson(rowData, TGS2Client.class);
    }
}
