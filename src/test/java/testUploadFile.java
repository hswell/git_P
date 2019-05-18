package test.java;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class testUploadFile {
}


/**
 * 基于TCP协议的Socket通信，实现文件传输-客户端
 */
class Client2 {
    public static void main(String[] args) {
        try {
            // 1.创建客户端的Socket，指定服务器的IP和端口
            Socket socket = new Socket("127.0.0.1", 9999);
            // 2.获取该Socket的输出流，用来向服务器发送文件
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(os);// 将BufferedOutputStream与套接字的输出流进行连接
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("F:/work/socketSample/filetemp/client/test_client.txt"));// 读取客户机文件
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = bufferedInputStream.read(buf)) != -1) {
                bufferedOutputStream.write(buf, 0, len);// 向BufferedOutputStream中写入数据
            }
            bufferedOutputStream.flush();// 刷新缓冲流
            socket.shutdownOutput();// 禁用此套接字的输出流

            // 3.获取输入流，取得服务器的信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("服务器端的信息：" + info);
            }
            socket.shutdownInput();// 禁用此套接字的输出流
            // 4.关闭资源
            os.close();
            bufferedInputStream.close();
            bufferedOutputStream.close();
            is.close();
            br.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}