package com.ezfun.guess.multithread.case005;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author SoySauce
 * @date 2019/10/31
 */
public class HeavySocketClient {
    private static ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("heavy-socket-pool-%d").build());
    private static final int SLEEP_TIME = 1000 * 1000 * 1000;

    public static class EchoClient implements Runnable {
        @Override
        public void run() {
            Socket client = null;
            PrintWriter writer = null;
            BufferedReader reader = null;
            try {
                client = new Socket();
                client.connect(new InetSocketAddress(8000));
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.print("H");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.print("e");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.print("l");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.print("l");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.print("o");
                LockSupport.parkNanos(SLEEP_TIME);
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(9));
                writer.print("!");
                LockSupport.parkNanos(SLEEP_TIME);
                writer.println();
                writer.flush();
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("from server: " + reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                    if (client != null) {
                        client.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        EchoClient ec = new EchoClient();
        for (int i = 0; i < 10; i++) {
            pool.execute(ec);
        }
    }


}
