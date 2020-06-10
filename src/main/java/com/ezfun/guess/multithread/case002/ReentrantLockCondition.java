package com.ezfun.guess.multithread.case002;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SoySauce
 * @date 2019/9/12
 */
public class ReentrantLockCondition implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();


    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println(lock.isHeldByCurrentThread());
            System.out.println("111");
            condition.await();
            System.out.println(lock.isHeldByCurrentThread());
            System.out.println(Thread.currentThread().getName()+" Thread is going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCondition rlc = new ReentrantLockCondition();
        Thread t1 = new Thread(rlc,"线程1");
        Thread t2 = new Thread(rlc,"线程2");
        t1.start();
        t2.start();
        Thread.sleep(2000);
        lock.lock();
//        condition.signalAll();
        condition.signal();
        lock.unlock();

    }
}
