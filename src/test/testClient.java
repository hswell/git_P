package test;

import zwatch.kerberos.AS.AS_Server;
import zwatch.kerberos.Client.Client;

public class testClient {
    static public void main(String argv[]) throws InterruptedException {
        Thread threadClient = new Client();
        threadClient.start();
        threadClient.join();
    }
}
