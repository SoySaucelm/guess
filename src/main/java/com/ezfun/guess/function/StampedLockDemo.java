package com.ezfun.guess.function;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * @author SoySauce
 * @date 2019/12/5
 */
public class StampedLockDemo {
    static Thread[] holdCpuThreads = new Thread[3];
    static final StampedLock lock = new StampedLock();


    public static void main(String[] args) throws InterruptedException {



        LongAdder longAdder = new LongAdder();
        System.out.println(1L<<7);
        System.out.println(0x80);
        boolean f=true || getFlag();
        int x=2;

        System.out.println(f);
        System.out.println(x=3);
        System.out.println(x);
        if (true){
            return;
        }

        new Thread() {
            @Override
            public void run() {
                long readLong = lock.writeLock();
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(20));
                lock.unlockWrite(readLong);
            }
        }.start();
        Thread.sleep(100);
        for (int i = 0; i < 3; i++) {
            holdCpuThreads[i] = new Thread(new HoldCPUReadThred());
            holdCpuThreads[i].start();
        }
        Thread.sleep(10000);
        //线程中断后,会占用CPU
        for (int i = 0; i < 3; i++) {
            holdCpuThreads[i].interrupt();

        }
    }

    private static boolean getFlag() {
        System.out.println(1111);
        return false;
    }


    private static class HoldCPUReadThred implements Runnable {
        @Override
        public void run() {
            long rl = lock.readLock();
            System.out.println(Thread.currentThread().getName() + " 获得读锁");
            lock.unlockRead(rl);
        }
    }
}
