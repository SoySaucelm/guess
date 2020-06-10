package com.ezfun.guess.multithread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SoySauce
 * @date 2020/1/6
 */
public class MultiThreadTest {
    static class UnsafeList {
        private static List list = new ArrayList();

        static class AddTask implements Runnable {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < 10000; i++) {
                    list.add(i);
                }
            }
        }

        public static void main(String[] args) {
            Thread t1 = new Thread(new AddTask(), "t1");
            Thread t2 = new Thread(new AddTask(), "t2");
            t1.start();
            t2.start();
            Thread t3 = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t3");
            t3.start();
        }


    }

}
