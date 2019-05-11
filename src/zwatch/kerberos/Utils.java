package zwatch.kerberos;

import com.google.gson.Gson;

import java.security.Key;
import java.util.Base64;

import static java.lang.System.*;

public class Utils {
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



    public long TimeStamp(){
        return currentTimeMillis();
    }

}
