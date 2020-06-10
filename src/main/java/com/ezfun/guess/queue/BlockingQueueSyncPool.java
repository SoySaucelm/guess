package com.ezfun.guess.queue;

import com.ezfun.guess.queue.dto.AbstractSyncDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SoySauce
 * @date 2019/9/9
 */
@Component
public class BlockingQueueSyncPool {
    //队列处理池
    private List<BlockingQueueLock<AbstractSyncDto>> pool = new ArrayList<>();
    //失败队列处理池
    private BlockingQueueLock<AbstractSyncDto> failureQueue;
    @Value("${pool.queue.count:5}")
    private int queueCount=5;
    @Value("${pool.queue.size:50}")
    private int queueSize=50;
    @Value("${pool.queue.consumerCount:3}")
    private int queueConsumerCount=3;
    @Value("${pool.failureQueue.size:100}")
    private int failureQueueSize=100;
    @Value("${pool.failureQueue.consumerCount:1}")
    private int failureQueueConsumerCount=1;
    @Autowired
    private HandlerFac handlerFac;
    private static AtomicInteger roundRobin = new AtomicInteger(1);

    @PostConstruct
    public void init() {
        for (int i = 0; i < queueCount; i++) {
            pool.add(new IBlockingQueueLock(queueSize, "syncQueue" + i, handlerFac, this, queueConsumerCount));
        }
        failureQueue = new IBlockingQueueLock(failureQueueSize, "syncRetryQueue", handlerFac, this,
                failureQueueConsumerCount);
    }


    public void push(AbstractSyncDto message) throws PoolFullException, InterruptedException {
        if (null != message) {
            BlockingQueueLock<AbstractSyncDto> queue = getQueue();
            queue.put(message);
        }
    }

    public void rePush(AbstractSyncDto message) throws PoolFullException, InterruptedException {
        if (null != message) {
            if (failureQueue.isFull()) {
                throw new PoolFullException("failureQueue is full", null);
            }
            failureQueue.put(message);
        }
    }

    private BlockingQueueLock<AbstractSyncDto> getQueue() throws PoolFullException {
        if (roundRobin.get() >= Integer.MAX_VALUE) {
            roundRobin.set(1);
        }
        int queueCount = pool.size();
        int index = (roundRobin.incrementAndGet() + queueCount) % queueCount;
        BlockingQueueLock<AbstractSyncDto> queue = pool.get(index);
        if (queue.isFull()) {
            if (pool.size() > 1) {
                index = (index + 1) < queueCount ? (index + 1) : 0;
                queue = pool.get(index);
                if (queue.isFull()) {
                    throw new PoolFullException("pool is full", null);
                }
            } else {
                throw new PoolFullException("pool is full", null);
            }
        }
        return queue;
    }

    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行的run()方法为Runnable接口实现");
            }
        }){
            public void run() {
                System.out.println("执行的run()方法为Thread类实现");
                super.run();
            }
        }.start();



    }

}
