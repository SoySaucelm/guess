package com.ezfun.guess.multithread.case001;

/**
 * 等待线程结束(join)
 * @author SoySauce
 * @date 2019/9/10
 */
public class JoinMain {
    public volatile static int i = 0;

    public static class AddThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i += 2000;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AddThread addThread = new AddThread();
        addThread.start();
        addThread.join();//表示无限等待,它会一直阻塞当前线程,直到目标线程执行完毕
        addThread.join(2001);//给出了一个最大等待时间,如果超过给定时间目标线程还在执行,当前线程也会因为等不及了,而继续执行下去

        System.out.println(i);
    }

}
