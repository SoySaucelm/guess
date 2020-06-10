package com.ezfun.guess.queue;

import com.ezfun.guess.queue.dto.AbstractSyncDto;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.text.MessageFormat;
import java.util.concurrent.*;

/**
 * @author SoySauce
 * @date 2019/9/6
 */
public class IBlockingQueueLock extends BlockingQueueLock<AbstractSyncDto> {


    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("queue-pool-%d").build();

    public IBlockingQueueLock(int capacity, String name, HandlerFac handlerFac, BlockingQueueSyncPool pool,
                              int consumerThreadSize) {
        super(capacity, name);
//        ExecutorService service = Executors.newFixedThreadPool(consumerThreadSize);
        ExecutorService service = new ThreadPoolExecutor(consumerThreadSize, consumerThreadSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), NAMED_THREAD_FACTORY, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < consumerThreadSize; i++) {
            QueueConsumer consumer = new QueueConsumer(this, handlerFac, pool);
            service.execute(consumer);
        }
    }

    public static void main(String[] args){
        String name = "tranCode={1}&username={1}";
        System.out.println(MessageFormat.format(name, "abc","ddd"));
    }
}
