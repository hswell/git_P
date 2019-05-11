package zwatch.kerberos.crypt;

import javax.crypto.Cipher;
import java.io.*;
import java.lang.reflect.Array;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSA {
    static Cipher cipher=null;

    static int MAX_ENCRYPT_ONCE=117;
    static int BLOCK_SIZE=128;

    public static void GenKey(String file) throws IOException, NoSuchAlgorithmException {
// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为1024位
        SecureRandom secrand = new SecureRandom();

        keyPairGen.initialize(1024, secrand);
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 生成私钥
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file+".pri"));
        oos.writeObject(privateKey);
        oos.flush();
        oos.close();
        oos = new ObjectOutputStream(new FileOutputStream(file+".pub"));
        oos.writeObject(publicKey);
        oos.flush();
        oos.close();
        System.out.println("make file ok!");
    }

    public static byte[] handleData(Key k, byte[] data, boolean encrypt)
            throws Exception
    {
        if(cipher==null){
            cipher= Cipher.getInstance("RSA");
        }
        if(k != null) {
            cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, k);
            return  cipher.doFinal(data);
        }else{
            return null;
        }
    }

    public static Key GetPriKey(String file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file+".pri"));
        RSAPrivateKey prikey = (RSAPrivateKey) ois.readObject();
        ois.close(); 		//使用公钥加密
        return prikey;
    }
    public static  Key GetPubKey(String file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file+".pub"));
        RSAPublicKey pubkey = (RSAPublicKey) ois.readObject();
        ois.close();
        return pubkey;
    }


}
