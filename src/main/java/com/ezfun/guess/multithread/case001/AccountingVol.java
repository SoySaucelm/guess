package com.ezfun.guess.multithread.case001;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SoySauce
 * @date 2019/9/10
 */
public class AccountingVol implements Runnable {
    static AccountingVol instance = new AccountingVol();
    static volatile int i = 0; //线程不安全
    static AtomicInteger atomicInteger = new AtomicInteger(0);//线程安全

    public void increase() {
        synchronized (this) {
            i++;
        }
        atomicInteger.incrementAndGet();
    }

    /**
     * 当前实例的锁
     * 两个线程必须是同一个AccountingVol对象
     * 这样才能保证两个线程在工作时,能够关注到同一个对象锁上面
     */
    public synchronized void increase2() {
        i++;
        atomicInteger.incrementAndGet();
    }

    /**
     * static方法块是当前类的锁而非当前实例
     */
    public synchronized static void increase3() {
        i++;
        atomicInteger.incrementAndGet();
    }

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            increase2();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(new AccountingVol());
//        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
        System.out.println(atomicInteger);

    }
}
