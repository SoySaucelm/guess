package com.ezfun.guess.multithread.case001;

/**
 * @author SoySauce
 * @date 2019/9/9
 */
public class InterruptedTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("---");
//                    System.out.println(Thread.interrupted());判断是否被中断,并清除当前中断状态
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("interrupted!");
                        break;
                    }
                    try {
                        Thread.sleep(2000); //由于中断而抛出异常,此时,它会清除中断标记
                    } catch (InterruptedException e) {
                        e.printStackTrace();
//                        throw new RuntimeException(e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                    Thread.yield();
                }
            }
        };
        t1.start();
        Thread.sleep(200);
        t1.interrupt();
    }
}
