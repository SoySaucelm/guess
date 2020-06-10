package com.ezfun.guess.multithread.case002;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁ReentrantLock与synchronized相比,重入锁有着显示的操作过程,我们必须手动指定何时加锁,解锁
 * 因此重入锁对逻辑控制的灵活性要远远好于synchronized
 * 重入锁是可以反复进入的,当然,这里的反复仅仅局限于一个线程
 * 如果一个线程多次获得锁,那么在释放锁的时候,也必须释放相同次数
 * @author SoySauce
 * @date 2019/9/11
 */
public class ReenterLock implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();

    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLock rl = new ReenterLock();
        Thread t1 = new Thread(rl);
        Thread t2 = new Thread(rl);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);



    }
}
