package test.kerberos;

import zwatch.kerberos.AS.AS_Server;
import zwatch.kerberos.Client.AS_proxy;
import zwatch.kerberos.Client.Client;

public class testC2AS {
    static public void main(String argv[]) throws InterruptedException {
        Thread threadAS = new AS_Server();
        threadAS.start();

        Thread threadClient = new Client();
        threadClient.start();

        threadClient.join();
        threadAS.join();


    }
}
