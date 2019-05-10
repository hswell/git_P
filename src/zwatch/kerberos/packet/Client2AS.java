package zwatch.kerberos.packet;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.sun.xml.internal.bind.v2.model.core.ID;
import zwatch.kerberos.Utils;

import java.io.StringReader;
import java.lang.management.ThreadInfo;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.SimpleFormatter;
/*
IDC|| IDtgs|| TS1

 */

public class Client2AS {
    public byte[] IDc, IDtgs;
    public long TS1;

    public Client2AS(byte[] IDc, byte[] IDtgs, long TS1){
        this.IDc= IDc;
        this.IDtgs=IDtgs;
        this.TS1=TS1;
    }

    public String pack(){
        return Utils.gson.toJson(this, Client2AS.class);
    };

    public static Client2AS unPack(String rowData){
        return Utils.gson.fromJson(rowData, Client2AS.class);
    }

}
