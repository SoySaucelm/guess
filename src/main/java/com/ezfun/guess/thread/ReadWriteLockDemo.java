package com.ezfun.guess.thread;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author SoySauce
 * @date 2019/5/7
 */
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock();

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static Lock readLock = readWriteLock.readLock();

    private static Lock writeLock = readWriteLock.writeLock();

    private int value;

    public int handleRead(Lock lock) {
        lock.lock();
        try {
            Thread.sleep(1000);
            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock, int var) {
        lock.lock();
        try {
            Thread.sleep(1000);
            value = var;
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLockDemo demo = new ReadWriteLockDemo();
        long start = System.currentTimeMillis();
        Runnable readRunnable = () -> {
//            demo.handleRead(readLock);
            demo.handleRead(lock);
        };
        Runnable writeRunnable = () -> {
//            demo.handleWrite(writeLock, new Random().nextInt());
            demo.handleWrite(lock, new Random().nextInt());
        };
        for (int i = 0; i < 15; i++) {
            Thread t = new Thread(readRunnable);
            t.start();
//            t.join();
        }
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(writeRunnable);
            t.start();
//            t.join();
        }
        Thread.sleep(6000);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
//        30266  17123
    }
}
