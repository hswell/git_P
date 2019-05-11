package zwatch.kerberos.packet;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
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

    private static Logger logger=Logger.getLogger("Pack.log");
    public String Uid="";
    public String TGSid="";
    public String TimeStamp = simpleFormatter.format(new Date());
    public String LifeTime = simpleFormatter.format(new Date());
    public String Kc_tgs = "";
    public String RowTicketTgs="";


    private static SimpleDateFormat simpleFormatter
            =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
    private static Gson gson=new Gson();

    public String CryptPack(String pass){
        String data=pack();
        byte[] CryptData= new byte[0];
        try {
            CryptData = DES.decrypt(data.getBytes() ,pass);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        StringWriter stringBuffer=new StringWriter();
        JsonWriter jsonWriter=new JsonWriter(stringBuffer);
        try {
            jsonWriter.beginObject()
                    .name("data").value(new String(Base64.getEncoder().encode(CryptData)))
                    .endObject();
            System.out.println(CryptData.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();

    };

    public String pack(){
        Gson gson=new Gson();
        return gson.toJson(this, AS2Client.class);
    };

    public static AS2Client unpack(String rowData, String pass){
        JsonReader reader=new JsonReader(new StringReader(rowData));
        try {
            reader.beginObject();
            if(reader.nextName().equals("data")){
                byte[] DcryptData= new byte[0];
                try {
                    String rowD=reader.nextString();
                    DcryptData = DES.decrypt(Base64.getDecoder().decode(rowD.getBytes()) ,pass, false);
                    String str= new String(DcryptData);
                    reader=new JsonReader(new StringReader(str));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Error");
            }
            return gson.fromJson(reader, AS2Client.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };

    public static AS2Client FromReader(Reader reader, String pass) throws IOException {
        return unpack(packetTool.FromReader(reader), pass);
    };

}
