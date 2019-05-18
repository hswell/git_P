package test.kerberos;

import zwatch.kerberos.Utils;

import java.lang.reflect.Array;
import java.util.Arrays;

public class testUtils {
    public static void main(String argv[]){
        for(int i=0; i<10; i++){
            byte[] rdk=Utils.RandomDesKey();
            System.out.println(Arrays.toString(rdk));
        }
    }
}
