package com.ezfun.guess.multithread.case004.other;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SoySauce
 * @date 2019/10/14
 */
public class SearchDemo {
    private static int[] arr = {0, 999, 222, 6, 3443, 88, 5, 4, 5, 45, 45, 666, 34, 432, 4, 34, 2};

    private static final int THREAD_NUM = Runtime.getRuntime().availableProcessors();

    private static AtomicInteger result = new AtomicInteger(-1);

    private static ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("search-pool").build());

    public static class SearchTask implements Callable<Integer> {
        private int searchValue, startPos, endPos;

        SearchTask(int searchValue, int startPos, int endPos) {
            this.searchValue = searchValue;
            this.startPos = startPos;
            this.endPos = endPos;
        }

        @Override
        public Integer call() throws Exception {
            return search(searchValue, startPos, endPos);
        }

        private Integer search(int searchValue, int startPos, int endPos) {
            for (int i = startPos; i < endPos; i++) {
                if (result.get() > 0) {
                    System.out.println("其它线程已经找到了11111");
                    return result.get();
                }
                if (arr[i] == searchValue) {
                    //如果设置失败,证明被其它线程找到了.
                    if (!result.compareAndSet(-1, i)) {
                        System.out.println("其它线程已经找到了222222");
                        return result.get();
                    }
                    System.out.println("第一个找到...");
                    return i;
                }
            }
            System.out.println("not find..");
            return -1;
        }
    }

    public static int pSearch(int v) throws ExecutionException, InterruptedException {
        int subArrSize = (arr.length % THREAD_NUM == 0) ? arr.length / THREAD_NUM : ((arr.length / THREAD_NUM) + 1);
        System.out.println(arr.length);
        System.out.println(subArrSize);
        List<Future<Integer>> result = Lists.newArrayList();
        for (int i = 0; i < THREAD_NUM; i++) {
            int start = i *subArrSize;
            int endPos = start + subArrSize;
            if (endPos > arr.length) {
                endPos = arr.length;
            }
            System.out.println("第一次：" + endPos);
        }

        for (int i = 0; i < arr.length; i += subArrSize) {
            int endPos = i + subArrSize;
            if (endPos > arr.length) {
                endPos = arr.length;
            }
            System.out.println("第二次：" + endPos);
            Future<Integer> future = executor.submit(new SearchTask(v, i, endPos));
            result.add(future);
        }
        for (Future<Integer> future : result) {
            if (future.get() > 0) {
                return future.get();
            }
        }
        return -1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int i = pSearch(6669);
        System.out.println(i);
    }
}
