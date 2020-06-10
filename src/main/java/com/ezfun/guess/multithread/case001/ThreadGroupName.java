package com.ezfun.guess.multithread.case001;

/**
 * 分门别类的管理:线程组
 * @author SoySauce
 * @date 2019/9/10
 */
public class ThreadGroupName implements Runnable {
    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName() + " --- " + Thread.currentThread()
                .getName();

        //noinspection InfiniteLoopStatement 忽略警告'while' statement cannot complete without throwing an exception
        while (true) {
            System.out.println("I am " + groupAndName);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        ThreadGroup tg = new ThreadGroup("printGroup");
        Thread t1 = new Thread(tg, new ThreadGroupName(), "t1");
        Thread t2 = new Thread(tg, new ThreadGroupName(), "t2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount());
        tg.list();

    }
}
