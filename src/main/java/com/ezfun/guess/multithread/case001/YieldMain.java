package com.ezfun.guess.multithread.case001;

/**
 * 谦让/礼让(yield)
 * @author SoySauce
 * @date 2019/9/10
 */
public class YieldMain {
    public static Object u = new Object();

    public static class T1 extends Thread {
        @Override
        public void run() {
           while (true){
               synchronized (u){
                   System.out.println("t1....");
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   Thread.yield();//这是一个静态方法,一旦执行他会使当前线程让出CPU,
                   // 让出CPU并不代表当前线程不执行了.当前线程让出CPU后,还会进行CPU资源的争夺

               }
           }
        }
    }
    public static class T2 extends Thread {
        @Override
        public void run() {
            while (true){
                synchronized (u){
                    System.out.println("t2....");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        t1.start();
        T2 t2 = new T2();
        t2.start();
    }

}
