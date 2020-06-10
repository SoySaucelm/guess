package com.ezfun.guess.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SoySauce
 * @date 2019/5/8
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(TraceThreadPoolExecutor.class);

    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, clientTrace(), Thread.currentThread().getName()));
    }

    private Exception clientTrace() {
        return new Exception("client stack trace");
    }

    private Runnable wrap(final Runnable task, final Exception clientStack, String clientThreadName) {
        return () -> {
            try {
                task.run();
            } catch (Exception e) {
                LOG.error("ThreadName:{},error:{}", clientThreadName, clientStack);
                clientStack.printStackTrace();
                throw e;
            }
        };
    }
}
