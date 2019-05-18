package test.kerberos;

import zwatch.kerberos.AS.AS_Server;
import zwatch.kerberos.Client.Client;
import zwatch.kerberos.TGS.TGS_Server;

public class testCAT {
    public static void main(String[] argv) throws InterruptedException {
        Thread threadAS = new AS_Server();
        threadAS.start();

        Thread threadTGS=new TGS_Server();
        threadTGS.start();

        Thread threadClient = new Client();
        threadClient.start();

        threadClient.join();
        threadAS.join();
        threadTGS.join();
    }
}
