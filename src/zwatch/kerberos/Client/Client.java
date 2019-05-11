package zwatch.kerberos.Client;

import zwatch.kerberos.packet.Client2TGS;

import java.io.IOException;

public class Client extends Thread implements IClient {

    @Override
    public void run() {

        System.out.println("-----------客户端启动-----------");
        AS_proxy as_proxy= new AS_proxy();
        as_proxy.run();
        try {
            as_proxy.Login("20161001742");
        }catch (IOException e){

            return;
        }
        String ticket_as= null;
        try {
            ticket_as = as_proxy.getRowTicket("20161001");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        TGS_proxy tgs_proxy=new TGS_proxy();
        tgs_proxy.Login(as_proxy.as2Client);
        String ticket_tgs= tgs_proxy.getRowTicket("20161001");

        //TODO 登录v服务器

    }

    @Override
    public void SaveConfig(String filename) {

    }

    @Override
    public void LoadConfig(String filename) {

    }
}
