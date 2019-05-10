package zwatch.kerberos.Client;

public class Client extends Thread implements IClient {

    @Override
    public void run() {
        System.out.println("-----------客户端启动-----------");
        AS_proxy as_proxy= new AS_proxy();
        as_proxy.run();
        as_proxy.Login("20161001742");
        as_proxy.getTicket("20161001");
    }

    @Override
    public void SaveConfig(String filename) {

    }

    @Override
    public void LoadConfig(String filename) {

    }
}
