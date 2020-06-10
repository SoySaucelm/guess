package com.ezfun.guess.queue;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SoySauce
 * @date 2019/9/6
 */
public class BlockingQueueLock<T> {
    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("queue-pool-%d").build();

    private static final ExecutorService POOL = new ThreadPoolExecutor(10, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    private int tail, head, count;
    String name;
    int size;
    private T[] elements;


    private final Lock lock = new ReentrantLock();
    /**
     *
     */
    private final Condition notFull = lock.newCondition();

    /**
     *
     */
    private final Condition notEmpty = lock.newCondition();

    BlockingQueueLock(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must Greater than 0");
        }
        this.size = capacity;
        elements = (T[]) new Object[capacity];
    }

    BlockingQueueLock(int capacity, String name) {
        this(capacity);
        this.name = name;
    }

    public synchronized T take() throws InterruptedException {
        try {
            lock.lock();
            while (isEmpty()) {
                System.out.println("isEmpty");
                notEmpty.await();
            }
            T element = elements[head];
            //不让队列在继续引用该元素,GC可以回收
            elements[head] = null;
            head++;
            if (head == elements.length) {
                head = 0;
            }
            count--;
            notFull.signal();
            return element;
        } finally {
            lock.unlock();
        }
    }

    public void put(T t) throws InterruptedException {
        try {
            lock.lock();
            while (isFull()) {
                System.out.println("isFull");
                notFull.await();
            }
            elements[tail] = t;
            tail++;
            if (tail == elements.length) {
                tail = 0;
            }

            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
//        notifyAll();
    }

    public boolean isFull() {
        try {
            lock.lock();
            return elements.length == count;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        try {
            lock.lock();
            return count == 0;
        } finally {
            lock.unlock();
        }
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public static void main(String[] args) throws Exception {

        String content1 =
                "1UwviYvqIKXfq2XOo3BL9XLUKCPldkOwW4rKXYoGbVD7xmfa9hu8Id0UEcm4JIdICHQgnNPgzyAA0Cv5AJ853nYSuUOSSjqBhjwuULCteEchlfl5R658CgLN76PeLiYF%2FkM8PlKWnkgu3ur7ABf9MyOHmga070FiUJO31o7oRRQLp%2FHHjVzYCXeYq0A%2BgFpU";
        String content = "YWdmZXJnZXJncmdyZw==";
        byte[] decode = new Base64().decode(URLDecoder.decode(content1));
        System.out.println(new String(decode));
        System.out.println(new String(decode,"gb2312"));
        System.out.println(new String(decode,"gbk"));
        System.out.println(new String(decode,StandardCharsets.ISO_8859_1));
        System.out.println(new String(decode,StandardCharsets.UTF_8));
        System.out.println(new String(decode,StandardCharsets.US_ASCII));

//        System.out.println(new String(Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8))));
        System.out.println(  new String(Base64Utils.decode(content.getBytes()),"utf-8"));
//         sout

//        BlockingQueueLock<Object> queue = new BlockingQueueLock<>(49);
//        POOL.submit(() -> {
//            for (int i = 0; i < 50000; i++) {
//                Object take = null;
//                try {
//                    take = queue.take();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(take);
//            }
//        });
//        POOL.submit(() -> {
//            int i = 0;
//            while (true) {
//                i++;
//                queue.put(i);
//            }
//        });

    }


    final static String DEATH_STRING = "{\"a\":\"\\x";

    public static void test_OOM() throws Exception {

        String line = new String("[{\\x22a\\x22:\\x22a\\xB1ph.\\xCD\\x86\\xBEI\\xBA\\xC3\\xBCiM+\\xCE\\xCE\\x1E\\xDF7\\x1E\\xD9z\\xD9Q\\x8A}\\xD4\\xB2\\xD5\\xA0y\\x98\\x08@\\xE1!\\xA8\\xEF^\\x0D\\x7F\\xECX!\\xFF\\x06IP\\xEC\\x9F[\\x85;\\x02\\x817R\\x87\\xFB\\x1Ch\\xCB\\xC7\\xC6\\x06\\x8F\\xE2Z\\xDA^J\\xEB\\xBCF\\xA6\\xE6\\xF4\\xF7\\xC1\\xE3\\xA4T\\x89\\xC6\\xB2\\x5Cx]");
        line = line.replaceAll("\\\\x", "%");
        String decodeLog = URLDecoder.decode(line, "UTF-8");
        System.out.println(decodeLog);
        try {
            Object obj = JSON.parse(decodeLog);
            obj = JSON.parse(DEATH_STRING);
        } catch (Exception e) {
            e.printStackTrace();
        }
//            assertEquals(e.getClass(), JSONException.class);
//            assertTrue(e.getMessage().contains("invalid escape character \\x"));
    }


}
