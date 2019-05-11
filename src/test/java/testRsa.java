package test.java;

import zwatch.kerberos.crypt.des;
import zwatch.kerberos.crypt.rsa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

public class testRsa {
public static void main(String[] args) throws Exception
        {
            Key pri=rsa.GetPriKey("testRsa.txt");
            Key pub=rsa.GetPubKey("testRsa.txt");

            String LongString="fiamsk1231231247398cnajskfWENUJINEUFUJWIENHIUFHEIUnaw" +
                    "adwnuifabwuadwaadfawefbuiehnuifhweufhuiewnufcdmaskcnasjfbasifbawu" +
                    "iejhnthis is a test";
            byte[] m=LongString.getBytes();
            byte[] c=rsa.handleData(pri, LongString.getBytes(), true);
            byte[] dem=rsa.handleData(pub, c, false);
            System.out.printf("m.length=%d, c.length=%d, dem.length=%d\n"
                    , m.length, c.length, dem.length);
            System.out.println(new String(dem));
        }
}
