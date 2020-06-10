package com.ezfun.guess.multithread.case002;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SoySauce
 * @date 2019/9/11
 */
public class FairLock implements Runnable {
    public static ReentrantLock fairLock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true) {
            try {
                fairLock.lock();
                System.out.println(Thread.currentThread().getName() + "获得锁");
            } finally {
                System.out.println(fairLock.isHeldByCurrentThread());
                    fairLock.unlock();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        FairLock fairLock = new FairLock();
        Thread t1 = new Thread(fairLock, "线程t1");
        Thread t2 = new Thread(fairLock, "线程t2");
        t1.start();
        t2.start();
    }
}
