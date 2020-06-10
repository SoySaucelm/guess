package com.ezfun.guess.multithread.case002;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author SoySauce
 * @date 2019/9/30
 */
public class RejectThreadPoolDemo {


    public static class MyTask implements Runnable {

        private String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {

            System.out.println(System.currentTimeMillis() + ":Thread ID:" + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        MyTask task = new MyTask();
        ThreadFactory googleThreadFactory = new ThreadFactoryBuilder().setNameFormat("RejectThreadPoolDemo")
                .setDaemon(true).build();
        BasicThreadFactory apacheThreadFactory = new BasicThreadFactory.Builder().namingPattern("RejectThreadPoolDemo")
                .daemon(true)
                .build();
//        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>(10);
        ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        SynchronousQueue<Runnable> synchronousQueue = new SynchronousQueue<>();


// 拒绝策略
// JDK内置提供了四种拒绝策略:(默认AbortPolicy)
// 1.AbortPolicy 改策略会直接抛出异常阻止系统正常工作
//   ExecutorService pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
//                linkedBlockingQueue, Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
// 2.CallerRunsPolicy 只要线程池未关闭 该策略直接在调用者线程中,运行当前被丢弃的任务,此策略虽不会丢弃任务,但任务提交线程的性能
//        极有可能急剧下降
//        new ThreadPoolExecutor.CallerRunsPolicy();
// 3.DiscardOldestPolicy 该策略将丢弃最老的一个请求,也就是即将被执行的一个任务,并尝试再次提交当前任务.
//        new ThreadPoolExecutor.DiscardOldestPolicy();
// 4.DiscardPolicy 该策略默默地丢弃无法处理的任务,不予任何处理.
//        new ThreadPoolExecutor.DiscardPolicy();
// 5.以上内置策略均实现了RejectedExecutionHandler接口,若以上策略仍无法满足需求可以自己扩展RejectedExecutionHandler
//        ExecutorService pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
//                linkedBlockingQueue, threadFactory, new RejectedExecutionHandler() {
//            @Override
//            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                System.out.println(r.toString() + "is discard");
//            }
//        });

//自定义线程创建:ThreadFactory
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                t.setName("MyThreadFactory");
                System.out.println("create " + t);
                return t;
            }
        };
//扩展线程池 ThreadPoolExecutor也是一个可以扩展的线程池,他提供了 beforeExecute afterExecute terminated 三个接口对线程进行控制
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                linkedBlockingQueue, Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(((MyTask) r).name + "准备执行:" + t.getName());//execute提交
//                    System.out.println(((FutureTask) r).get() + "准备执行:" + t.getName());//submit提交
//                    String name = ((MyTask) ((Executors.RunnableAdapter) ((FutureTask) r).callable).task).name;
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println(((MyTask) r).name + "执行完成:" + JSON.toJSONString(r));
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };

        MyTask task;
        for (int i = 0; i < 2; i++) {
            task = new MyTask("task-name:" + i);
            pool.execute(task);
//            pool.submit(task);

//            Thread.sleep(0);

        }
//        Thread.sleep(1000);
        pool.shutdown();
        System.out.println("shutdown...");
//        Thread.sleep(5000);
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
