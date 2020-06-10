package com.ezfun.guess.multithread.case001;

/**
 * 挂起(suspend)和继续执行(resume)线程
 * @author SoySauce
 * @date 2019/9/10
 */
public class GoodSuspend {
    public static Object u = new Object();
    public static class ChangeObjectThread extends Thread{
        volatile boolean suspendme = false;
        public void suspendMe(){
            suspendme = true;
        }
        public void resumeMe(){
            suspendme = false;
            synchronized(this){
                notify();
            }
        }
        //anoinspection InfiniteLoopStatement 忽略警告'while' statement cannot complete without throwing an exception

        @Override
        public void run() {
            //noinspection InfiniteLoopStatement
            while (true){
                synchronized (this){
//                    while (suspendme){
                    if (suspendme)
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
                synchronized (u){
                    System.out.println("in ChangeObjectThread");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.yield();
            }
        }

    }
    public static class ReadObjectThread extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (u){
                    System.out.println("in ReadObjectThread");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread t1 = new ChangeObjectThread();
        ReadObjectThread t2 = new ReadObjectThread();
        t1.start();
        t2.start();
        Thread.sleep(1000);
        t1.suspendMe();
        System.out.println("suspend t1 3 sec");
        Thread.sleep(3000);
        System.out.println("resume t1");
        t1.resumeMe();
    }
}
