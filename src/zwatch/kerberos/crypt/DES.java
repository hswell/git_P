package zwatch.kerberos.crypt;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Logger;

public class DES {
    static Logger logger= Logger.getLogger("Cipher.log");
    static SecretKeyFactory keyFactory = null;
    static Cipher cipher = null;
    static SecureRandom random = new SecureRandom();

    public static void init() throws NoSuchAlgorithmException, NoSuchPaddingException {
        keyFactory = SecretKeyFactory.getInstance("DES");
        cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    }
    public static byte[] decrypt(byte[] src, byte[] password) throws InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(src, password, true);
    }
    public static byte[] decrypt(byte[] src, byte[] password, boolean e) throws InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {
        if(keyFactory== null || cipher == null){
            init();
        }
        DESKeySpec desKey = new DESKeySpec(password);
        SecretKey securekey = keyFactory.generateSecret(desKey);
        cipher.init(e?Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, securekey, random);
        return cipher.doFinal(src);
    }
}
