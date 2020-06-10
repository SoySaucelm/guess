package com.ezfun.guess.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author SoySauce
 * @date 2019/5/7
 */
public class LockSupportDemo {
    static Object obj = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");


    static class ChangeObjectThread extends Thread {

        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (obj) {
                System.out.println("in>>>>" + getName());
                LockSupport.park();
                System.out.println(Thread.currentThread().isInterrupted());
                System.out.println(Thread.currentThread().isInterrupted());
                System.out.println(Thread.interrupted());
                System.out.println(Thread.interrupted());
                if (Thread.interrupted()) {
                    System.out.println(getName() + "被中断了..");
                }
            }
            System.out.println(getName() + "执行完成..");

        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
        Thread.sleep(2000);
        t1.interrupt();
        Thread.sleep(1000);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();

    }
}
