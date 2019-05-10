package test.java;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class testSocket {
public static void main(String[] argv) {
    int port = 9999;
    System.out.println("-----------服务器启动-----------");
    ServerSocket server = null;
    try {
        server = new ServerSocket(port);
    } catch (IOException e) {
        e.printStackTrace();
    }
    Socket socket = null;
    try {
        socket = server.accept();
    } catch (IOException e) {
        e.printStackTrace();
    }
    Reader reader = null;
    try {
        reader = new InputStreamReader(socket.getInputStream());
    } catch (IOException e) {
        e.printStackTrace();
    }
    char chars[] = new char[1024];
        int len = 0;
        StringBuilder builder = new StringBuilder();
        while (true) {
            try {
                if (!((len=reader.read(chars)) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.append(new String(chars, 0, len));
        }
        System.out.println("收到来自客户端的信息： " + builder);
    try {
        reader.close();
        socket.close();
        server.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

    }
}
