package com.ezfun.guess.multithread.case002;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SoySauce
 * @date 2019/9/12
 */
public class TimeLock implements Runnable{
    ReentrantLock lock=new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(1,TimeUnit.SECONDS)){
                Thread.sleep(2000);
            }else {
                System.out.println("get lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
    public static void main(String[] args){
        TimeLock tl = new TimeLock();
        Thread t1 = new Thread(tl);
        Thread t2 = new Thread(tl);
        t1.start();
        t2.start();
    }
}
