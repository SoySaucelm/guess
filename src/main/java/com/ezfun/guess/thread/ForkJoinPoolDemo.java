package com.ezfun.guess.thread;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author SoySauce
 * @date 2019/5/8
 */
public class ForkJoinPoolDemo {

    static class CountTask extends RecursiveTask<Long> {
        private static final int threshold = 10000;
        private static final int MAX_NUM = 10000;
        private long start;
        private long end;

        public CountTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long sum = 0;

            boolean canCompute = (end - start) < threshold;
            if (canCompute) {
                for (long i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                //简单分成为MAX_NUM个小任务
                long step = (start + end) / MAX_NUM;
                ArrayList<CountTask> subTasks = new ArrayList<>();
                long pos = start;
                for (int i = 0; i < MAX_NUM; i++) {
                    long lastOne = pos + step;
                    if (lastOne > end) lastOne = end;
                    CountTask countTask = new CountTask(pos, lastOne);
                    pos += step + 1;
                    subTasks.add(countTask);
                    countTask.fork();
                }
                for (CountTask t : subTasks) {
                    sum += t.join();
                }

            }
            return sum;
        }

        public static void main(String[] args) {
            ForkJoinPool pool = new ForkJoinPool();
            CountTask task = new CountTask(0, 20000);
            ForkJoinTask<Long> r = pool.submit(task);
            try {
                Long result = r.get();
                System.out.println("sum = " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        int start = 0, end = 3;
        long step = (start + end) / 100;
        long pos = start;
        long sum = 0;
        for (int i = 0; i < 10; i++) {
            long lastOne = pos + step;
//            System.out.println(pos + ">>>>" + lastOne);
            pos += step + 1;
        }
        for (long i = start; i <= end; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(20000000/1000000);
        for (int i = 0; ; ) {

        }
    }
}
