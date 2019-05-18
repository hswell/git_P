package test.java;

import java.nio.charset.Charset;

public class testByte2String {
    public static void main(String[] argv){
        byte[] hello="你好啊".getBytes(Charset.forName("UTF-8"));
        for (int i=0; i<hello.length; i++){
            System.out.printf("%d = %d\n", i, (int)hello[i]);
        }
        //System.out.println("hello = "+new String(hello, Charset.forName("ASCII")));
    }
}
