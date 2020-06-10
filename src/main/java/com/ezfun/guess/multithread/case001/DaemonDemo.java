package com.ezfun.guess.multithread.case001;

/**
 * 守护线程(Daemon)
 * 守护线程是一种特殊的线程,它是系统的守护者
 * @author SoySauce
 * @date 2019/9/10
 */
public class DaemonDemo {
    public static class DaemonThread extends  Thread{
        @Override
        public void run() {
            //noinspection InfiniteLoopStatement
            while (true){
                System.out.println("i am alive");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        DaemonThread t = new DaemonThread();
        t.setDaemon(true);
        t.start();
        Thread.sleep(2000);
    }
}
