package com.ezfun.guess.multithread.case002;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程阻塞工具类
 *
 * @author SoySauce
 * @date 2019/9/26
 */
public class LockSupportDemo {
    public static Object u = new Object();
    public static ReentrantLock lock = new ReentrantLock();
    public static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    public static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    @SuppressWarnings("all")
    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
//            synchronized (u) {
//            lock.lock();
//            LockSupport.unpark(this);
            System.out.println("in " + getName());
            LockSupport.park();
            if (Thread.interrupted()) {
                System.out.println(getName() + "被中断了");
            }
//            lock.unlock();
//            }
            System.out.println(getName() + "执行结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        Thread mt = Thread.currentThread();
        LockSupport.unpark(mt);
        LockSupport.park(obj);
        new Thread(new UnPark(mt)).start();
        LockSupport.park(obj);
        System.out.println("thread is park");
        t1.start();
        Thread.sleep(100);
        t2.start();

//        LockSupport.unpark(t1);
//        t1.interrupt();
//        LockSupport.unpark(t2);
        t1.join();
        t2.join();


    }
    public static void main2(String[] args){
        System.out.println(TimeUnit.SECONDS.toNanos(8));
        long l = System.currentTimeMillis();
        System.out.println("start:"+l);
        long l1 = TimeUnit.SECONDS.toNanos(8);
        LockSupport.parkNanos(1000 * 1000 * 1000 * 8L);
        System.out.println("end:"+(System.currentTimeMillis()-l));
    }

    static class UnPark implements Runnable {
        private Thread t;

        public UnPark(Thread t) {
            this.t = t;
        }

        @Override
        public void run() {
            System.out.println("unPark start");
            LockSupport.unpark(t);
            System.out.println("unPark success");
        }
    }
}
