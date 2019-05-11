package zwatch.kerberos.packet;

import com.google.gson.Gson;
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
    public String Uid="";
    public String Kc_v="";
    public String TimeStamp = simpleFormatter.format(new Date());
    public String LifeTime = simpleFormatter.format(new Date());
    public String RowTicket_v="";

    private static SimpleDateFormat simpleFormatter
            =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
    static Gson gson=new Gson();

    public String CryptPack(String pass){
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
        return gson.toJson(this, TGS2Client.class);
    }

    public TGS2Client UnPack(String rowData){
        return gson.fromJson(rowData, TGS2Client.class);
    }
}
