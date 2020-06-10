package com.ezfun.guess.multithread.case002;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 允许多个线程同时访问:信号量(Semaphore)
 *
 * @author SoySauce
 * @date 2019/9/26
 */
public class SemaphoreDemo implements Runnable {

    //构造信号量对象时,必须要指定信号量的准入数,即同时能申请多少个许可

    final Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try {
            //从信号量中获取一个许可
            semaphore.acquire();
            //模拟耗时操作
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId() + " done!");
            //释放一个许可(在释放许可之前，必须先获获得许可。)
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        SemaphoreDemo demo = new SemaphoreDemo();
        for (int i = 0; i < 20; i++) {
            exec.submit(demo);
        }

    }


}
