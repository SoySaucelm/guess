package com.ezfun.guess.multithread.case001;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用volatile申明一个变量时,就等于告诉虚拟机,这个变量极有可能会被某些程序或者线程修改,为了确保变量被修改后,应用程序范围的所有线程
 * 都能看到这个改动, 虚拟机就必须采用一些特殊的手段(不能随意变化优化目标指令如:指令重排等),保证这个变量的可见性等特点
 * volatile无法保证一些复合操作的原子性如:i++等
 * @author SoySauce
 * @date 2019/9/10
 */
@SuppressWarnings("all")
public class VolatileTest {
    static AtomicInteger atomicInteger = new AtomicInteger(0);
    static volatile int i = 0;

    public static class PlusTask implements Runnable {

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                atomicInteger.incrementAndGet();
                i++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int j = 0; j < 10; j++) {
            threads[j] = new Thread(new PlusTask());
            threads[j].start();
        }
        for (int j = 0; j < 10; j++) {
            threads[j].join();
        }
        System.out.println(i);
        System.out.println(atomicInteger);
    }
}
