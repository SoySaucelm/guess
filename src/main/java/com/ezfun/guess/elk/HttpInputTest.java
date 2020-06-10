package com.ezfun.guess.elk;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 * @author SoySauce
 * @date 2020/3/5
 */
public class HttpInputTest {

    static String host = "47.99.40.191";
    static int port = 8730;
    static String message = "new ?????req>>>>[title]:UDP[param]:ScreenObserver>>>>>temperature0.022.00.0";
//    static String message = "123";


    public static void main2(String[] args) {
        try {
            //客户端请求与本机在20006端口建立TCP连接
            Socket client = new Socket(host, port);
            client.setSoTimeout(10000);
            //获取键盘输入
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            //获取Socket的输出流，用来发送数据到服务端
            PrintStream out = new PrintStream(client.getOutputStream());
            out.println(message);
            boolean b = out.checkError();
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取Socket的输入流，用来接收从服务端发送过来的数据  
//        BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
//        boolean flag = true;
//        while(flag){
//            System.out.print("输入信息：");
//            String str = input.readLine();
//            //发送数据到服务端
//            out.println(str);
//            if("bye".equals(str)){
//                flag = false;
//            }else{
//                try{
//                    //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
//                    String echo = buf.readLine();
//                    System.out.println(echo);
//                }catch(SocketTimeoutException e){
//                    System.out.println("Time out, No response");
//                }
//            }
//        }
//        input.close();
//        if(client != null){
//            //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
//            client.close(); //只关闭socket，其关联的输入输出流也会被关闭
//        }
    }

    public static void main22(String[] args) throws IOException {
//        docker run --rm --network host -it -v /etc/logstash/conf.d/elk.conf:/usr/share/logstash/config/logstash.conf docker.elastic.co/logstash/logstash:7.2.0
        try (DatagramSocket socket = new DatagramSocket()) {

            byte[] byteArray = ("{\"name\":\"露西\",\"age\":\"18\"}").getBytes(Charset.defaultCharset());
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, InetAddress.getByName(host), 8612);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(8000));
        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();
        int bytesSent = channel.send(buf, new InetSocketAddress(host, port));
        System.out.println(bytesSent);
        try {
            //客户端请求与本机在20006端口建立TCP连接
            Socket client = new Socket(host, port);
            client.setSoTimeout(10000);
            //获取键盘输入
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            //获取Socket的输出流，用来发送数据到服务端
            PrintStream out = new PrintStream(client.getOutputStream());
            out.println(message);
            boolean b = out.checkError();
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args){
        try (DatagramSocket socket = new DatagramSocket()) {
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < 65508; i++) {
                message.append(0);
            }
            byte[] byteArray = (message.toString()).getBytes(Charset.defaultCharset());
            DatagramPacket packet = new DatagramPacket(byteArray, byteArray.length, InetAddress.getByName(host), 8612);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
