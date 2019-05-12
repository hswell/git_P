package zwatch.kerberos;

import com.google.gson.Gson;
import zwatch.kerberos.crypt.DES;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import static java.lang.System.*;

public class Utils {
    public static long Default_Lifetime=600000000;

    public static Gson gson=new Gson();
    public static Base64.Decoder base64de=Base64.getDecoder();
    public static Base64.Encoder base64en=Base64.getEncoder();

    public static String encrypt_des(String data, String k) throws Exception {
        //TODO
        byte[] ret= new byte[0];
        try {
            ret = DES.decrypt(data.getBytes(), k,true);
        } catch (InvalidKeyException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("错误");
        }
        return base64en.encodeToString(ret);
    }

    public static String decrypt_des(String data, String k) throws Exception {
        //TODO
        byte[] ret= base64de.decode(data);
        try {
            ret = DES.decrypt(ret, k,false);
        } catch (InvalidKeyException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("错误");
        }
        return new String(ret);
    }



    public static long TimeStamp(){
        return currentTimeMillis();
    }


    static public String FromReader(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        boolean DataEoF=false;
        int len=0;
        int MAX_BUFFER_SIZE=4096;
        char[] chars=new char[MAX_BUFFER_SIZE];
        while (!DataEoF) {
            len = reader.read(chars);
            if ( len <= 0){
                DataEoF=true;
                break;
            }
            if(len<MAX_BUFFER_SIZE){
                DataEoF = true;
            }
            builder.append(new String(chars, 0, len));
        }
        return builder.toString();
    };


    public static byte[] RandomDesKey(){
        byte[] ret=null;
        try {
            SecureRandom sr = new SecureRandom();
            // 为我们选择的DES算法生成一个KeyGenerator对象
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            kg.init(sr);
            Key key = kg.generateKey();
            ret=key.getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
