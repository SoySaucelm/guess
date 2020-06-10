package com.ezfun.guess.multithread.case002;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒计时器
 *
 * @author SoySauce
 * @date 2019/9/26
 */
public class CountDownLatchDemo implements Runnable {
    static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(10);
    static final CountDownLatchDemo DEMO = new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("check complete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            COUNT_DOWN_LATCH.countDown();
        }
    }

    @SuppressWarnings("all")
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 15; i++) {
            exec.submit(DEMO);
        }
        //等待检查
        COUNT_DOWN_LATCH.await();
        //发射
        System.out.println("fire!");
        exec.shutdown();
    }
}
