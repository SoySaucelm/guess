package com.ezfun.guess.multithread.case005;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SoySauce
 * @date 2019/10/29
 */
public class MultiThreadEchoServer {
    //使用线程池来处理每一个客户端连接
    private static ExecutorService tp = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            6000L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), new BasicThreadFactory.Builder().namingPattern
            ("tp-pool-%d").build());

    /**
     * 由一个客户端Socket构造而成
     */
    static class HandleMsg implements Runnable {
        Socket clientSocket;

        public HandleMsg(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        /**
         * 任务是读取这个Socket的内容并将其进行返回
         */
        @Override
        public void run() {
            BufferedReader is = null;
            PrintWriter os = null;
            try {
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintWriter(clientSocket.getOutputStream(), true);
//                while (true) {




//                BufferedReader finalIs = is;
//                    new Thread(){
//                        @Override
//                        public void run() {
//                            String line= null;
//                            try {
//                                line = finalIs.readLine();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            System.out.println("from client:" + line);
//                        }
//                    }.start();
                String line = null;
                long start = System.currentTimeMillis();
                //从inputStream中读取客户端所发送的数据
                while ((line = is.readLine()) != null) {
//                        Scanner scanner = new Scanner(System.in);
//                        String content = scanner.nextLine();
                    os.println("hi client:" + line);
//                    os.flush();
                }
                long end = System.currentTimeMillis();
                System.out.println("spend:" + (end - start) + "ms");
//                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket echoServer = null;
        Socket clientSocket;
        try {
            echoServer = new ServerSocket(8000);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (true) {
            try {
                clientSocket = echoServer.accept();
                System.out.println(clientSocket.getRemoteSocketAddress() + " connect!");
                tp.execute(new HandleMsg(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
