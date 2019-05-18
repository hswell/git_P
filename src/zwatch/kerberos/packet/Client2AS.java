package zwatch.kerberos.packet;

import zwatch.kerberos.Utils;

/*
IDC|| IDtgs|| TS1

 */

public class Client2AS {
    public String IDc, IDtgs;
    public long TS1;

    public Client2AS(byte[] IDc, byte[] IDtgs, long TS1){
        this.IDc= new String(IDc, Utils.ascii_chset);
        this.IDtgs= new String(IDtgs);
        this.TS1=TS1;
    }

    public String pack(){
        return Utils.gson.toJson(this, Client2AS.class);
    };

    public static Client2AS unPack(String rowData){
        return Utils.gson.fromJson(rowData, Client2AS.class);
    }

}
