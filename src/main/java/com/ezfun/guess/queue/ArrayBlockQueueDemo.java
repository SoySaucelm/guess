package com.ezfun.guess.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author SoySauce
 * @date 2019/10/11
 */
public class ArrayBlockQueueDemo {

    static class Consumer implements Runnable {
        private ArrayBlockingQueue<String> queue;

        public Consumer(ArrayBlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String take = this.queue.take();
                    System.out.println("take:" + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(5);
        Thread t = new Thread(new Consumer(arrayBlockingQueue));
        t.setDaemon(true);
        t.start();
        for (int i = 0; i < 20; i++) {
            arrayBlockingQueue.put(String.format("添加:%d", i));
            System.out.println("put:" + i);
        }
    }
}
