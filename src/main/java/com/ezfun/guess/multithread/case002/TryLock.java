package com.ezfun.guess.multithread.case002;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SoySauce
 * @date 2019/9/12
 */
public class TryLock implements Runnable {


    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();
    int num;

    public TryLock(int num) {
        this.num = num;
    }

    @Override
    public void run() {

        System.out.println(lock2.hashCode());
        System.out.println(lock1.hashCode());
        if (num == 1) {
            while (true) {
                if (lock1.tryLock()) {
                    try {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lock2.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getName() + ":My Job done " + num);
                                return;
                            } finally {
                                lock2.unlock();
                            }
                        }
                    } finally {
                        lock1.unlock();
                    }
                }
            }
        } else {
            while (true) {
                if (lock2.tryLock()) {
                    try {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (lock1.tryLock()) {
                            try {
                                System.out.println(Thread.currentThread().getId() + ":My Job done " + num);
                                return;
                            } finally {
                                lock1.unlock();
                            }
                        }
                    } finally {
                        lock2.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TryLock tl1 = new TryLock(1);
        TryLock tl2 = new TryLock(2);
        Thread t1 = new Thread(tl1);
        Thread t2 = new Thread(tl2);
        t1.start();
        Thread.sleep(10);
        t2.start();
    }
}
