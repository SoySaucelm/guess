package com.ezfun.guess.queue;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author SoySauce
 * @date 2019/9/6
 */
public class BlockingQueueSyc<T> {
    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("queue-pool-%d").build();

    private static final ExecutorService POOL = new ThreadPoolExecutor(10, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    private int tail, head, count;
    private T[] elements;

    BlockingQueueSyc(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must Greater than 0");
        }
        elements = (T[]) new Object[capacity];
    }

    public synchronized T take() throws InterruptedException {
        while (isEmpty()) {
            System.out.println("isEmpty");
            wait();
        }
        T element = elements[head];
        elements[head] = null;
        head++;
        if (head == elements.length) {
            head = 0;
        }
        count--;
        notifyAll();
        return element;
    }

    public synchronized void put(T t) throws InterruptedException {
        while (isFull()) {
            System.out.println("isFull");
            wait();
        }
        elements[tail] = t;
        tail++;
        if (tail == elements.length) {
            tail = 0;
        }
        count++;
        notifyAll();
    }

    public synchronized boolean isFull() {
        return elements.length == count;
    }

    public synchronized boolean isEmpty() {
        return count == 0;
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueueSyc<Object> queue = new BlockingQueueSyc<>(49);
        POOL.submit(() -> {
            for (int i = 0; i < 50000; i++) {
                Object take = null;
                try {
                    take = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println(take);
            }
        });
        POOL.submit(() -> {
            int i = 0;
            while (true){
                i++;
                queue.put(i);
            }
//            for (int i = 0; i < 50; i++) {
//                try {
//                        queue.put(i);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        });
//        Thread.sleep(6000);
        POOL.submit(() -> {
            for (int i = 0; i < 500; i++) {
                Object take = null;
                try {
                    take = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(take);
            }
        });
        POOL.submit(() -> {
            for (int i = 0; i < 500; i++) {
                Object take = null;
                try {
                    take = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(take);
            }
        });


    }

}
