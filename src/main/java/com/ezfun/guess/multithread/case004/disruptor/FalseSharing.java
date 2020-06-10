package com.ezfun.guess.multithread.case004.disruptor;

/**
 * 伪共享
 * http://ifeve.com/false-shareing-java-7-cn/
 * https://www.jianshu.com/p/e5af0e4a8b86
 *
 * @author SoySauce
 * @date 2019/10/14
 */
public class FalseSharing implements Runnable {
    //线程数
    private final static int NUM_THREADS = 4;
    //迭代次数
    private final static long ITERATIONS = 500L * 1000L * 1000L;

    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }
    }

    public FalseSharing(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }

    }

    //    private static class VolatileLong {
//        public volatile long value = 0L;
//        //comment out
//        public long p1,p2,p3, p4, p5, p6;
//
////                , p2, p3, p4, p5, p6, p7;
//    }
    //jdk7以上使用此方法(jdk7的某个版本oracle对伪共享做了优化)
    public final static class VolatileLong {
        public volatile long value = 0L;
        public long p1, p2, p3, p4, p5, p6, p7;
//        因为用7个下标保证了会有56个字节填充在数值的任何一边，56字节的填充+8字节的long数值正好装进一行64字节的缓存行。
    }

    // jdk7以下使用此方法
//    public final static class VolatileLong
//    {
//        public long p1, p2, p3, p4, p5, p6, p7; // cache line padding
//        public volatile long value = 0L;
//        public long p8, p9, p10, p11, p12, p13, p14; // cache line padding
//
//    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        runTest();
        //duration(持续时间):  21437  duration(持续时间):  4259 duration(持续时间):  4120 duration(持续时间):  4203
        // duration(持续时间):  32076 duration(持续时间):  21075 duration(持续时间):  24004 duration(持续时间):  24035
        System.out.println("duration(持续时间):  " + (System.currentTimeMillis() - start));
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
    }
}
