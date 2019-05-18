package test.java;

import java.io.IOException;
import java.net.ServerSocket;

public class testSocket2 {
    public static void main(String[] argv){



    }
}


class Client extends Thread{
    @Override
    public void run() {
        super.run();

        try {
            ServerSocket server=new ServerSocket(12323);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class Server extends Thread{
    @Override
    public void run() {
        super.run();
    }
}