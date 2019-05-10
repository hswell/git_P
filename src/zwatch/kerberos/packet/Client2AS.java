package zwatch.kerberos.packet;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.lang.management.ThreadInfo;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.SimpleFormatter;
/*
消息(1)新增的元素包括：
Realm：标识用户所属的域
Options:用于请求在返回的票据中设置指定的标志位，如下所述。
Times:用于客户端请求在票据中设置时间：
    from:请求票据的起始时间
    till: 请求票据的过期时间
    rtime: 请求till 更新时间
Nonce:在消息(2)中重复使用的临时交互号，用于确保应答是刷新的，且未被攻击者使用。
 Options||IDc||Realm_c||IDtgs||Times||Nonce1

 */

public class Client2AS {

    public String Uid="20161001742";
    public String TGSid="tgs0";
    public String TimeStamp = simpleFormatter.format(new Date());

    private static SimpleDateFormat simpleFormatter
            =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
    private static Gson gson=new Gson();

    public Client2AS(){ }

    public String pack(){
        Gson gson=new Gson();
        return gson.toJson(this, Client2AS.class);
    };

    public static Client2AS unpack(String rowData){
        JsonReader reader=new JsonReader(new StringReader(rowData));
        return gson.fromJson(reader, Client2AS.class);
    }

}
