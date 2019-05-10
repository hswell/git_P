package zwatch.kerberos;

import com.google.gson.Gson;

import javax.crypto.KeyGenerator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import static java.lang.System.*;

public class Utils {
    public static long Default_Lifetime=60000;

    public static Gson gson=new Gson();
    public static Base64.Decoder base64de=Base64.getDecoder();
    public static Base64.Encoder base64en=Base64.getEncoder();

    public static String encrypt_des(String data, Key k){
        //TODO
        return null;
    }

    public static String decrypt_des(String data, Key k){
        //TODO
        return null;
    }



    public static long TimeStamp(){
        return currentTimeMillis();
    }


    static public String FromReader(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        boolean DataEoF=false;
        int len=0;
        char[] chars=new char[1024];
        while (!DataEoF) {
            len = reader.read(chars);
            if ( len <= 0){
                DataEoF=true;
                break;
            }
            if(chars[len-1]=='}'){
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
