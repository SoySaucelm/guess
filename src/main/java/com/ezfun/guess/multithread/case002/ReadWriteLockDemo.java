package com.ezfun.guess.multithread.case002;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock 读写锁
 * 读写分离锁可以有效地帮助减少锁竞争
 * @author SoySauce
 * @date 2019/9/26
 */
@Slf4j
@SuppressWarnings("all")
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;

    public Object handleRead(Lock lock) throws InterruptedException {
        try {
            //模拟读操作
            lock.lock();
            //读操作的耗时越多,读写锁的优势就越明显
            Thread.sleep(1000);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void handleWrite(Lock lock, int index) throws InterruptedException {
        try {
//            log.info("handleWrite index: " + index);
            //模拟写操作
            lock.lock();
            Thread.sleep(1000);
            value = index;
//            log.info("write value " + value);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Object o = readWriteLockDemo.handleRead(readLock);
//                Object o = readWriteLockDemo.handleRead(lock);
                    log.info("handleRead val:" + o);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
//        Runnable readRunnable = () -> {
//            try {
//                Object o = readWriteLockDemo.handleRead(readLock);
////                Object o = readWriteLockDemo.handleRead(lock);
//                log.info("handleRead val:" + o);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        };
        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int num = 20;
//                int num = new Random().nextInt(20);
                    readWriteLockDemo.handleWrite(writeLock, num);
//                readWriteLockDemo.handleWrite(lock, num);
                    log.info("write val:" + num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 18; i++) {
            new Thread(readRunnable).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(writeRunnable).start();
        }

        System.out.println("****");
    }

}
