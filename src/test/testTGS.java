package test;

import zwatch.kerberos.TGS.TGS_Server;

public class testTGS {
    public static void main(String[] argv) throws InterruptedException {
        Thread threadTGS=new TGS_Server();
        threadTGS.start();
        threadTGS.join();
    }
}
