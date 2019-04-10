package com.ezfun.guess.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

/**
 * Created by liming on 2019/1/4.
 */
public class ThreadTest {


    public static void interruptedTest() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (Boolean.TRUE) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("is interrupted");
                        break;
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        System.out.println("interrupted when sleep");
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("1>>>>>>>>>");
                    Thread.yield();

                }
            }
        };
        t1.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        t1.interrupt();
    }


    static final Object obj = new Object();

    public static void waitNotifyTest() throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                synchronized (obj) {
                    System.out.println(System.currentTimeMillis() + "  t1 start >>>>>>>>>>>");
                    try {
                        System.out.println(System.currentTimeMillis() + " t1 wait for obj");
                        obj.wait();
//                        LockSupport.park();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(System.currentTimeMillis() + " t1 end >>>>>>>>");
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                synchronized (obj) {
                    System.out.println(System.currentTimeMillis() + " t2 start >>>>>>>>>>>  notify t1");
                    obj.notify();

                    System.out.println(System.currentTimeMillis() + " t2 end >>>>>>>>");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        t2.start();
//        LockSupport.unpark(t1);
        t1.join();
        t2.join();

    }

    public static int i = 0;

    public static void add() throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int j = 0; j < 10000; j++) {
                    i++;
                }
                try {
                    Thread.sleep(9999);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        };
        t.start();
//        t.join();
        System.out.println(i);

    }

    public static void increase() throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int j = 0; j < 10; j++) {
            threads[j] = new Thread(() -> {
//                synchronized (obj){
                for (int k = 0; k < 1000; k++) {
                    i++;
//                    }
                }
            });
            threads[j].start();
        }
        for (int k = 0; k < 10; k++) {
            threads[k].join();
        }
        System.out.println(i);
    }

    public static volatile int num = 0;

    public static class MyThread extends Thread {
        @Override
        public void run() {
            num++;
//            System.out.println(Thread.currentThread().getName());
//            while (!ready) {
//                System.out.println(number);
//            }
        }
    }

    public static boolean ready;
    public static int number;

    public static void noVisibility() throws InterruptedException {
//        new Thread(() -> {
//            while (!ready) {
//                System.out.println(number);
//            }
//        }).start();
        new MyThread().start();
        Thread.sleep(2000);
        number = 66;
//        Thread.sleep(0);
        ready = true;
        Thread.sleep(2000);

    }

    public static void threadGroupTest() throws InterruptedException {
        ThreadGroup tg = new ThreadGroup("test");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String groupName = Thread.currentThread().getThreadGroup().getName() + "_" + Thread.currentThread().getName();
                while (true) {
                    System.out.println("i am" + groupName);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread t1 = new Thread(tg, runnable, "t1");
        Thread t2 = new Thread(tg, runnable, "t2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount());
        tg.list();
        Thread.sleep(3000);
        tg.stop();
    }

    public static void multiThread() {

        for (int j = 0; j < 500; j++) {
            new MyThread().start();
        }
        System.out.println(num);
    }

    public static void daemonThread() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("i am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(2000);
    }

    public static void priority() {
        Thread t1 = new Thread(() -> {
            int count = 0;
            while (true) {
                synchronized (obj) {
                    if (++count > 5000) {
                        System.out.println("hightPriority  is complete");
                        break;
                    }
                }
            }
        });
        Thread t2 = new Thread(() -> {
            int count = 0;
            while (true) {
                synchronized (obj) {
                    if (++count > 5000) {
                        System.out.println("lowPriority  is complete");
                        break;
                    }
                }
            }
        });
        t1.setPriority(Thread.MAX_PRIORITY);
        t2.setPriority(Thread.MIN_PRIORITY);
        t2.start();
        t1.start();
    }


    public static class AccountingSync implements Runnable {
        static AccountingSync account = new AccountingSync();
        static volatile int incrNum = 0;
        static AtomicInteger atomicInteger = new AtomicInteger(0);

        public synchronized void increase() {
            incrNum++;
        }

        public void increaseAtomic() {
            atomicInteger.incrementAndGet();
        }

        @Override
        public void run() {
            for (int j = 0; j < 8000; j++) {
                synchronized ("") {
                    increase();
                }
                increaseAtomic();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            Thread t1 = new Thread(new AccountingSync());
            Thread t2 = new Thread(new AccountingSync());
            Thread t3 = new Thread(new AccountingSync());
            t1.start();
            t2.start();
            t3.start();
            t1.join();
            t2.join();
            System.out.println(incrNum);
            System.out.println(atomicInteger.get());

        }
    }

    public static void multiThread2ArrayList() throws InterruptedException {
        ArrayList list = new ArrayList();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 500; j++) {
//                    synchronized (obj) {
                    list.add(j);
//                    }
                }
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(list.size());
    }

    public static class MapMultiThread {

        static HashMap map = new HashMap<>();
        static ConcurrentHashMap cMap = new ConcurrentHashMap();

        public static class AddThread implements Runnable {
            int start = 0;

            public AddThread(int start) {
                this.start = start;
            }

            @Override
            public void run() {
                for (int j = start; j < 5000; j += 2) {
                    synchronized (obj) {
                        map.put(String.valueOf(j), Integer.toBinaryString(j));
                        cMap.put(String.valueOf(j), Integer.toBinaryString(j));
                    }
                }
            }
        }

        public static void main(String[] args) throws InterruptedException {
            Thread t1 = new Thread(new AddThread(0));
            Thread t2 = new Thread(new AddThread(5));
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println(map.size());
            System.out.println(cMap.size());
        }
    }

    public static void multiThread2HashMap() throws InterruptedException {
        HashMap map = new HashMap();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < 500; j++) {
//                    synchronized (obj) {
                    map.put(String.valueOf(j), Integer.toBinaryString(j));
//                    }
                }
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map.size());
    }

    public static class ReenterLock implements Runnable {
        public static ReentrantLock lock = new ReentrantLock();
        public static int lockNum = 0;

        @Override
        public void run() {
            for (int j = 0; j < 5000; j++) {
                lock.lock();
                lock.lock();
                lockNum++;
                lock.unlock();
                lock.unlock();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            ReenterLock rl = new ReenterLock();
            Thread t1 = new Thread(rl);
            Thread t2 = new Thread(rl);
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            System.out.println(lockNum);
        }
    }

    public static class InterruptedLock implements Runnable {
        static ReentrantLock lock1 = new ReentrantLock();
        static ReentrantLock lock2 = new ReentrantLock();
        int lockNum;

        /**
         * 控制加锁顺序,方便构造死锁
         *
         * @param lockNum
         */
        public InterruptedLock(int lockNum) {
            this.lockNum = lockNum;
        }

        @Override
        public void run() {
            try {
                if (lockNum == 1) {
//                    lock1.lock();
                    lock1.lockInterruptibly();
                    try {
                        Thread.sleep(500);
                        System.out.println("lock1 work.....");
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + ":线程出错" + lockNum);
                        lock2.lockInterruptibly();
                    }
                } else {
//                    lock2.lock();
                    lock2.lockInterruptibly();
                    try {
                        Thread.sleep(500);
                        System.out.println("lock2 work.....");
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + ":线程出错" + lockNum);
                        lock1.lockInterruptibly();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + ":线程出错");
                e.printStackTrace();
            } finally {
                if (lock1.isHeldByCurrentThread())
                    lock1.unlock();
                if (lock2.isHeldByCurrentThread())
//                    lock2.unlock();
                    System.out.println(Thread.currentThread().getName() + ":线程退出");
            }
        }

        public static void main(String[] args) throws InterruptedException {
            InterruptedLock il1 = new InterruptedLock(1);
            InterruptedLock il2 = new InterruptedLock(2);
            Thread t1 = new Thread(il1, "一号");
            Thread t2 = new Thread(il2, "二号");
            t1.start();
            t2.start();
            Thread.sleep(100);
            //中断其中一个线程
            t2.interrupt();
        }
    }

    public static class TimeLock implements Runnable {
        public static ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            try {
                if (lock.tryLock(5, TimeUnit.SECONDS)) {
                    Thread.sleep(1000);
                } else {
                    System.out.println("get lock failed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }

        public static void main(String[] args) {
            TimeLock timeLock = new TimeLock();
            Thread t1 = new Thread(timeLock);
            Thread t2 = new Thread(timeLock);
            t1.start();
            t2.start();

        }
    }

    public static class TryLock implements Runnable {
        public static ReentrantLock lock1 = new ReentrantLock();
        public static ReentrantLock lock2 = new ReentrantLock();
        int lockNum;

        public TryLock(int lockNum) {
            this.lockNum = lockNum;
        }

        @Override
        public void run() {
            if (lockNum == 1) {
                while (true) {
                    if (lock1.tryLock()) {
                        try {
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (lock2.tryLock()) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + ":My Job done");
                                    return;
                                } finally {
                                    lock2.unlock();
                                }
                            }
                        } finally {
                            lock1.unlock();
                        }
                    }
                }
            } else {
                while (true) {
                    if (lock2.tryLock()) {
                        try {
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (lock1.tryLock()) {
                                try {
                                    System.out.println(Thread.currentThread().getId() + ":My Job done");
                                    return;
                                } finally {
                                    lock1.unlock();
                                }
                            }
                        } finally {
                            lock2.unlock();
                        }
                    }
                }
            }
        }

        public static void main(String[] args) {
            TryLock tl1 = new TryLock(1);
            TryLock tl2 = new TryLock(2);
            Thread t1 = new Thread(tl1);
            Thread t2 = new Thread(tl2);
            t1.start();
            t2.start();
        }
    }

    public static class FairLock implements Runnable {
        public static ReentrantLock lock = new ReentrantLock(true);

        @Override
        public void run() {
            while (true) {
                try {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "获得锁");
                } finally {
                    lock.unlock();
                }
            }
        }

        public static void main(String[] args) {
            FairLock fairLock = new FairLock();
            Thread t1 = new Thread(fairLock, "t1");
            Thread t2 = new Thread(fairLock, "t2");
            t1.start();
            t2.start();
        }
    }

    public static class ReenterLockCondition implements Runnable {
        public static ReentrantLock lock = new ReentrantLock();

        public static Condition condition = lock.newCondition();

        @Override
        public void run() {
            try {
                lock.lock();
                condition.await();
                System.out.println("Thread is going on");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            ReenterLockCondition rlc = new ReenterLockCondition();
            Thread t1 = new Thread(rlc);
            t1.start();
            Thread.sleep(2000);
            TimeUnit.NANOSECONDS.toNanos(1);
            //通知线程t1继续执行
            lock.lock();//没有这一步会抛出 java.lang.IllegalMonitorStateException
//            synchronized ()
            condition.signal();
            lock.unlock();

        }
    }

    /**
     * 允许多个线程同时访问:信号量(semaphore)
     */
    public static class SemaphoreDemo implements Runnable {
        final Semaphore semaphore = new Semaphore(5);

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + ":done!");
                Thread.sleep(2000);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            ExecutorService exec = Executors.newFixedThreadPool(20);
            SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
            for (int j = 0; j < 20; j++) {
                exec.submit(semaphoreDemo);
            }
            exec.shutdown();
        }
    }

    /**
     * ReadWriteLock 读写锁
     */
    public static class ReadWriteLockDemo {
        private static Lock lock = new ReentrantLock();
        private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private static Lock readLock = readWriteLock.readLock();
        private static Lock writeLock = readWriteLock.writeLock();
        private int value;

        public Object handleRead(Lock lock) throws InterruptedException {
            try {
                lock.lock();//模拟读操作
                //读操作的耗时越多,读写锁的优势就越明显
                Thread.sleep(1000);
                return value;
            } finally {
                lock.unlock();
            }
        }

        public void handleWrite(Lock lock, int index) throws InterruptedException {
            try {
                lock.lock();
                Thread.sleep(1000);
                value = index;
            } finally {
                lock.unlock();
            }
        }

        public static void main(String[] args) {
            ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
            Runnable readRunnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        readWriteLockDemo.handleRead(readLock);
//                        readWriteLockDemo.handleRead(lock);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Runnable writeRunnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        readWriteLockDemo.handleWrite(writeLock, new Random().nextInt());
//                        readWriteLockDemo.handleWrite(lock,new Random().nextInt());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            for (int j = 0; j < 18; j++) {
                new Thread(readRunnable).start();
            }
            for (int j = 0; j < 2; j++) {
                new Thread(writeRunnable).start();

            }
        }
    }

    public static class CountDownLatchDemo implements Runnable {
        static final CountDownLatch end = new CountDownLatch(10);
        static final CountDownLatchDemo demo = new CountDownLatchDemo();

        @Override
        public void run() {
            //模拟检查任务
            try {
                Thread.sleep(new Random().nextInt(10) * 1000);
                System.out.println("check complete");
                end.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            ExecutorService exec = Executors.newFixedThreadPool(10);
            for (int j = 0; j < 10; j++) {
                exec.submit(demo);
            }
            //等待检查
            end.await();
            //发射火箭
            System.out.println("fire!");
            exec.shutdown();

        }
    }

    public static class CyclicBarrierDemo {
        public static int cyclicNum;

        public static class Soldier implements Runnable {
            private String soldier;
            private final CyclicBarrier cyclicBarrier;

            public Soldier(String soldier, CyclicBarrier cyclicBarrier) {
                this.soldier = soldier;
                this.cyclicBarrier = cyclicBarrier;
            }

            @Override
            public void run() {
                try {
                    //等待所有士兵到齐
                    cyclicBarrier.await();
                    doWork();
                    //等待所有士兵完成工作
                    cyclicBarrier.await();
//                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void doWork() {
                try {
                    for (int j = 0; j < 10000; j++) {
                        cyclicNum++;
                    }

//                    Thread.sleep(300);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(soldier + ":任务完成");
            }
        }

        public static class BarrierRun implements Runnable {
            boolean flag;
            int N;

            public BarrierRun(boolean flag, int n) {
                this.flag = flag;
                N = n;
            }

            @Override
            public void run() {
                if (flag) {
                    System.out.println("司令:[士兵" + N + "个,任务完成]");
                    System.out.println(cyclicNum);
                } else {
                    System.out.println("司令:[士兵" + N + "个,集合完毕]");
                    this.flag = true;
                }
            }
        }

        public static void main(String[] args) throws InterruptedException {
            final int N = 5;
            Thread[] allSoldier = new Thread[N];
            boolean flag = false;
            CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRun(flag, N));
            //设置屏障点 主要为了执行这个方法
            System.out.println("集合队伍!");
            for (int j = 0; j < N; j++) {
                System.out.println("士兵" + j + "报道");
                allSoldier[j] = new Thread(new Soldier("士兵" + j, cyclicBarrier));
                allSoldier[j].start();
            }
//            for (int j = 0; j <5 ; j++) {
//                allSoldier[j].join();
//            }
//            try {
//                Thread.sleep(9999);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(cyclicNum);


        }
    }

    public static class LockSupportDemo {
        static Object obj = new Object();
        static ChangeObjectThread t1 = new ChangeObjectThread("t1");
        static ChangeObjectThread t2 = new ChangeObjectThread("t2");

        public static class ChangeObjectThread extends Thread {
            public ChangeObjectThread(String name) {
                super.setName(name);
            }

            @Override
            public void run() {
                synchronized (obj) {
                    System.out.println("in " + getName());
                    LockSupport.park();
                    if(Thread.interrupted()){
                        System.out.println(getName()+"被中断了");
                    }
                }
                System.out.println(getName()+"执行结束");
            }
            public static void main(String[] args) throws InterruptedException {
                  t1.start();
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                t2.start();
                Thread t3 = new Thread(() -> {
                    System.out.println(Thread.currentThread().getName());
                    LockSupport.unpark(t2);
                    synchronized (obj) {
                        System.out.println("in " + Thread.currentThread().getName());
                        LockSupport.park();
                        if (Thread.interrupted()) {
                            System.out.println(Thread.currentThread().getName() + "被中断了");
                        }
                    }
                },"t3");
//                synchronized (obj){
//                    System.out.println("....");
//                    obj.notify();
//                    obj.notify();
//                    obj.notify();
//                }
                t3.start();
                t1.interrupt();
//                LockSupport.unpark(t2);
//                obj.notify();
//                obj.notify();
//                LockSupport.unpark(t1);
//                LockSupport.unpark(t2);
//                t1.join();
//                t2.join();

            }
        }
    }

    public static class ScheduledExecutorServiceDemo{
        public static void main(String[] args){
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);
//            ses.scheduleWithFixedDelay(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println(System.currentTimeMillis()/1000);
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, 1, 2, TimeUnit.SECONDS);  //上一个任务结束后 间隔时间


            ses.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(System.currentTimeMillis()/1000);
                }
            }, 1, 2, TimeUnit.SECONDS);//上一个任务开始 间隔时间
        }
    }




    public static void main(String[] args) throws InterruptedException, ExecutionException {


//        System.out.println(future.get());
//        interruptedTest();
//        waitNotifyTest();
//        add();
//        increase();
//        noVisibility();
//        threadGroupTest();
//        multiThread();
//        daemonThread();
      /*  for (int j = 0; j < 10; j++) {
            priority();
        }*/
//        multiThread2ArrayList();
//        int x = 3123213;
//        System.out.println(Integer.toBinaryString(x)+"   "+Integer.toString(x));
//        multiThread2HashMap();
//        myPrint(3);
//        TimeUnit.NANOSECONDS.toNanos(1);
//        System.out.println(com.ezfun.guess.demo.TimeUnit.ABC.toNanos(1));
    }

    public static void myTest() {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        ArrayList<Future> list = new ArrayList();
        for (int j = 0; j < 10; j++) {
            Future future = exec.submit(new Callable() {
                @Override
                public Object call() throws Exception {
                    System.out.println("Asynchronous Callable");
                    return "Callable Result";
                }
            });
            list.add(future);
        }

        list.forEach(future -> {
            try {
                System.out.println("future.get() = " + future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        exec.shutdown();
    }

    public static void myPrint(int num) {
//        System.out.println(num);
        if (num < 1) {
            System.out.println("结束啦");
        } else {
            myPrint(num - 1);
//            myPrint(num-1);
            System.out.println("once more....1...." + num);
//            myPrint(num-1);

        }
        System.out.println("once more....2...." + num);

    }
}
