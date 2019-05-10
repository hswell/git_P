package test.java;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class testJson {

    @SerializedName("NAME")
    private String m_name="聂";


    private static String m_name2="2";
    public static void main(String[] argv){
        Gson object = new Gson();
        testJson test=new testJson();
        String ret=object.toJson(test);
        System.out.println(ret);
        test=object.fromJson(ret, testJson.class);
        System.out.println(test.m_name);
    };
}
