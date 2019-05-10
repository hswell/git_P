package zwatch.kerberos.packet;

import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;

public class packetTool {

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
                DataEoF=true;
            }
            builder.append(new String(chars, 0, len));
        }
        return builder.toString();
    };
}
