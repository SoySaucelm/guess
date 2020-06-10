package com.ezfun.guess.multithread.case002;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author SoySauce
 * @date 2019/9/29
 */
public class ThreadPoolDemo {

    public static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ":Thread Id:" +
                    Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @SuppressWarnings("all")
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MyTask myTask = new MyTask();
//        ExecutorService pool = Executors.newFixedThreadPool(5);
//        ExecutorService pool = Executors.newCachedThreadPool();
//        ExecutorService pool = Executors.newSingleThreadExecutor();
        ExecutorService pool = Executors.newSingleThreadScheduledExecutor();

        ThreadFactory googleThreadFactory = new ThreadFactoryBuilder().setNameFormat("threadPoolDemo")
                .setDaemon(false).build();
        BasicThreadFactory apacheThreadFactory = new BasicThreadFactory.Builder().namingPattern("threadPoolDemo")
                .daemon(true).build();
        ThreadPoolExecutor googleThreadPool=new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), googleThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        ThreadPoolExecutor apacheThreadPool=new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), apacheThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 0; i++) {
//            ((ScheduledExecutorService) pool).schedule(() -> {
//                System.out.println("schedule:I DON'T WILL COME BACK");
//            }, 1000, TimeUnit.MILLISECONDS);
            ((ScheduledExecutorService) pool).scheduleAtFixedRate(() -> {
                System.out.println("scheduleAtFixedRate:I WILL COME BACK,GIRL");
                System.out.println(System.currentTimeMillis());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }, 1, 1000, TimeUnit.MILLISECONDS); //间隔耗时 5000 以上一个任务开始时间计算
//            ((ScheduledExecutorService) pool).scheduleWithFixedDelay(() -> {
//                System.out.println("scheduleWithFixedDelay:I WILL COME BACK,GIRL");
//                System.out.println(System.currentTimeMillis());
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }, 100,1000, TimeUnit.MILLISECONDS); //间隔耗时 6000 以上一个任务结束时间计算
//            pool.submit(myTask);

        }

        //堆栈去哪里了:在线程池中寻找堆栈
        for (int i = 0; i < 5; i++) {
            //我们应该会得到5个结果 但真正运行只有4个
//            googleThreadPool.submit(new DivTask2(100,i));
            //解决方法1 放弃submit使用execute
            googleThreadPool.execute(new DivTask2(100,i));
            //或者改造submit
//            Future<?> r = googleThreadPool.submit(new DivTask2(100, i));
//            r.get();

        }
    }

    static class DivTask2 implements Runnable {
        public int x, y;

        public DivTask2(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void run() {
            double r = x / y;
            System.out.println(r);
        }
    }
}
