package com.ezfun.guess.multithread.case002;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SoySauce
 * @date 2019/10/10
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void execute(Runnable task) {
        super.execute(wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    private Runnable wrap(Runnable task, Exception clientStack, String clientThreadName) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    clientStack.printStackTrace();
                    throw e;
                }
            }
        };
    }

    private Exception clientTrace() {
        return new Exception("Client stack trace");
    }
    
    public static void main(String[] args){
        TraceThreadPoolExecutor pool = new TraceThreadPoolExecutor(0, 200, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
        for (int i = 0; i < 5; i++) {
            pool.execute(new ThreadPoolDemo.DivTask2(100,i));
        }
    }
}
