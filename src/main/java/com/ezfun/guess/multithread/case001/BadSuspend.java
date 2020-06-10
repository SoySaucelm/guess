package com.ezfun.guess.multithread.case001;

/**
 * 挂起(suspend)和继续执行(resume)线程
 * @author SoySauce
 * @date 2019/9/10
 */
public class BadSuspend {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");
    public static class ChangeObjectThread extends Thread{

        public ChangeObjectThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("in "+getName());
                Thread.currentThread().suspend();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
          t1.start();
          Thread.sleep(100);
          t2.start();
          t1.resume();
        Thread.sleep(100);
          t2.resume();
          t1.join();
          t2.join();
    }
}
