package zwatch.kerberos;

import com.google.gson.Gson;

import java.util.Base64;

import static java.lang.System.*;

public class Utils {
    public static Gson gson=new Gson();
    public static Base64.Decoder base64de=Base64.getDecoder();
    public static Base64.Encoder base64en=Base64.getEncoder();

    public long TimeStamp(){
        return currentTimeMillis();
    }
}
