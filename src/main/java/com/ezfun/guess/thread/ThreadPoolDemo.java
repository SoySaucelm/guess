package com.ezfun.guess.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author SoySauce
 * @date 2019/5/7
 */
public class ThreadPoolDemo {

    static class MyTask implements Runnable {
        public String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + "Thread id: " + Thread.currentThread().getId() +
                    ",task name " + name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class DivTask implements Callable {
        public int x, y;

        public DivTask(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public Map<String, String> call() throws Exception {
            HashMap<String, String> map = new HashMap<>(1);
            map.put(x + "/" + y, String.valueOf(x / y));
            return map;
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

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
//                .setNameFormat("demo-pool-%d").build();
//        ExecutorService service = Executors.newFixedThreadPool(5);
//        ExecutorService service = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
//        ThreadFactory threadFactory = Executors.cust;

        ExecutorService service = new ThreadPoolExecutor(3, 3,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(5),
                r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(false);
                    t.setName("demo-pool-%" + t.getId());
                    System.out.println("create " + t);
                    return t;
                },
                (r, executor) -> {
                    System.out.println("rejectedExecution>>>>" + r.toString() + executor.toString());
                }) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行: " + ((MyTask) r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行任务完成: " + ((MyTask) r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };


        ExecutorService service2 = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(50),
                r -> new Thread(r));

        ExecutorService service3 = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,
                new SynchronousQueue<>());

        List<Future<?>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
//            MyTask myTask2 = new MyTask("task-name-" + i);
            DivTask2 myTask = new DivTask2(100, i);
//            Future<HashMap> submit = service2.submit(myTask2, new HashMap(16));
//            list.add();
            service3.execute(myTask);
        }
        for (Future<?> future : list) {
            try {
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        service.shutdown();
    }
}
