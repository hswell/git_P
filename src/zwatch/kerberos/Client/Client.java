package zwatch.kerberos.Client;

import zwatch.kerberos.Utils;
import zwatch.kerberos.packet.Client2TGS;
import zwatch.kerberos.ticket.Authenticator_v;

import java.io.IOException;

public class Client extends Thread implements IClient {
    String ticket_v=null;
    String auth=null;
    String pass=null;
    String user=null;

    public Client(){

    }
    public Client(String user, String password){
        this.user=user;
        this.pass=password;
    }

    @Override
    public void run() {

        System.out.println("-----------客户端启动-----------");
        AS_proxy as_proxy= new AS_proxy();
        as_proxy.run();
        byte[] Kc_tgs=null;
        try {
            as_proxy.Login(user);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }
        String ticket_as= null;
        try {
            ticket_as = as_proxy.getRowTicket(pass);
            Kc_tgs=as_proxy.as2Client.Kc_tgs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        TGS_proxy tgs_proxy=new TGS_proxy();
        tgs_proxy.Login(as_proxy.as2Client);
        //tgs_proxy.getRowTicket();
        //TODO 登录v服务器
        Utils.default_ticket_v=tgs_proxy.getRowTicket(Kc_tgs);
        try {
            Utils.default_auth= new Authenticator_v(user , "", Utils.TimeStamp()).cryptPack( tgs_proxy.tgs2Client.Kc_v);
        } catch (Exception e) {
            Utils.default_auth=null;
            e.printStackTrace();
        }
    }

    @Override
    public void SaveConfig(String filename) {

    }

    @Override
    public void LoadConfig(String filename) {

    }
}
