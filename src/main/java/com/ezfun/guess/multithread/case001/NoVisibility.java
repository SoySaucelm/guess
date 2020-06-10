package com.ezfun.guess.multithread.case001;

/**
 * 可以使用java虚拟机参数-server切换到Server模式 由于系统优化 readerThread无法看到主线程中的修改,导致readerThread永远无法退出
 *
 * @author SoySauce
 * @date 2019/9/10
 */
public class NoVisibility {
    static volatile boolean ready; //volatile保证数据的可见性和有序性
    static int number;


    public static class ReaderThread extends Thread {

        @Override
        public void run() {
            // noinspection InfiniteLoopStatement
            while (!ready);
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String content;
        content = "a:1";
        new ReaderThread().start();
        Thread.sleep(100);
        number = 42;
        ready = true;
        Thread.sleep(1000);
    }
}
