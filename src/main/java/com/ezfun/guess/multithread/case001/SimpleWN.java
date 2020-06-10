package com.ezfun.guess.multithread.case001;

/**
 * 等待wait和通知notify
 * @author SoySauce
 * @date 2019/9/9
 */
public class SimpleWN {
    final static Object object = new Object();

    public static class T1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":t1 start");
                try {
                    System.out.println(System.currentTimeMillis() + ":t1 wait for object");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":t1 end");
            }
        }
    }

    public static class T2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":t2 start notify one thread");
                object.notify();
                System.out.println(System.currentTimeMillis() + ":t2 end");
                try {
                    Thread.sleep(2000); //wait()会释放目标对象的锁,sleep不会释放任何资源
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        T1 t1 = new T1();
        T2 t2 = new T2();
        t1.start();
        t2.start();

    }
}
