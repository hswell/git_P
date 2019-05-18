package test.java;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public class testSocketClient {
public static void main(String[] argv) {
        String host = "127.0.0.1";
        int port = 9999;
        System.out.println("-----------客户端启动-----------");
    Socket client = null;
    try {
        client = new Socket(host, port);
    } catch (IOException e) {
        e.printStackTrace();
    }
    Writer writer = null;
    try {
        assert client != null;
        writer = new OutputStreamWriter(client.getOutputStream());
    } catch (IOException e) {
        e.printStackTrace();
    }
    Scanner in = new Scanner(System.in);
    try {
        if (writer != null) {
            writer.write(in.nextLine());
            writer.flush();
            writer.close();
        }
        client.close();
        in.close();
    } catch (IOException e) {
        e.printStackTrace();
    }catch (NullPointerException e){
        e.printStackTrace();
    }
}
}
