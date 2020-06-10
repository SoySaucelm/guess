package com.ezfun.guess.multithread.case001;

/**
 * 线程优先级
 *
 * @author SoySauce
 * @date 2019/9/10
 */
public class PriorityDemo {
    public static class HighPriority extends Thread {
        static int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if (count > 10000) {
                        System.out.println("HighPriority is complete");
                        break;
                    }
                }
            }

        }
    }

    public static class LowPriority extends Thread {
        static int count = 0;

        @Override
        public void run() {
            while (true) {
                synchronized (PriorityDemo.class) {
                    count++;
                    if (count > 10000) {
                        System.out.println("LowPriority is complete");
                        break;
                    }
                }
            }

        }
    }

    public static void main(String[] args) {
        HighPriority high = new HighPriority();
        LowPriority low = new LowPriority();
        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);
        low.start();
        high.start();
    }


}
