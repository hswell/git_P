package test;

import zwatch.kerberos.AS.AS_Server;
import zwatch.kerberos.Client.Client;

public class testServer {
    static public void main(String argv[]) throws InterruptedException {
        Thread threadAS = new AS_Server();
        threadAS.start();
        threadAS.join();
    }
}
