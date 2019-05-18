package test.java;

import zwatch.kerberos.crypt.RSA;

import java.security.Key;

public class testRsa {
public static void main(String[] args) throws Exception
        {
            Key pri= RSA.GetPriKey("testRsa.txt");
            Key pub= RSA.GetPubKey("testRsa.txt");

            String LongString="fiamsk1231231247398cnajskfWENUJINEUFUJWIENHIUFHEIUnaw" +
                    "adwnuifabwuadwaadfawefbuiehnuifhweufhuiewnufcdmaskcnasjfbasifbawu" +
                    "iejhnthis is a test";
            byte[] m=LongString.getBytes();
            byte[] c= RSA.handleData(pri, LongString.getBytes(), true);
            byte[] dem= RSA.handleData(pub, c, false);
            System.out.printf("m.length=%d, c.length=%d, dem.length=%d\n"
                    , m.length, c.length, dem.length);
            System.out.println(new String(dem));
        }
}
