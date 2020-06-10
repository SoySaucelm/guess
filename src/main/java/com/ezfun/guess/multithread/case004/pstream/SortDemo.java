package com.ezfun.guess.multithread.case004.pstream;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SoySauce
 * @date 2019/10/15
 */
public class SortDemo {
    static int[] arr;
    static int excFlag = 1;

    static ExecutorService pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L,
            TimeUnit.SECONDS, new SynchronousQueue<>(), new BasicThreadFactory.Builder()
            .namingPattern("odd-even-sort-pool")
            .daemon(true)
            .build());

    static synchronized void setExcFlag(int v) {
        excFlag = v;
    }

    static synchronized int getExcFlag() {
        return excFlag;
    }

    public static class OddEvenSortTask implements Runnable {
        int i;
        CountDownLatch latch;
        int[] arr;

        public OddEvenSortTask(int[] arr, int i, CountDownLatch latch) {
            this.arr = arr;
            this.i = i;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println(String.format("thread name:%s thread id:%s", Thread.currentThread().getName(), Thread
                    .currentThread().getId()));
            if (arr[i] > arr[i + 1]) {
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
                setExcFlag(1);
            }
            latch.countDown();
        }
    }

    public static void oddEvenSort(int[] arr) {
        int excFlag = 1, start = 0;
        while (excFlag == 1 || start == 1) {
            excFlag = 0;
            for (int i = start, len = arr.length - 1; i < len; i += 2) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    excFlag = 1;
                }
            }
            if (start == 0) {
                start = 1;
            } else {
                start = 0;
            }
        }
    }

    /**
     * 奇偶排序
     *
     * @param array
     */
    public static void batcherSort(int[] array) {
        int length = array.length;
        boolean flag = true;
        while (true) {
            flag = true;
            for (int i = 1; i < length - 1; i += 2) {
                System.out.println(i);
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                    flag = false;
                }
            }
//            0   1   2   3   4   5   6   7   8   9     length:10
//            11, 95, 45, 15, 78, 84, 51, 24, 12  33
//            0       2       4       6       8
//            11      45      78      51
//                1       3       5       7
//                95      15      84      24
            for (int i = 0; i < length - 1; i += 2) {
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
            ;
            printArr(array);
        }
    }

    public static void pOddEvenSort(int[] arr) throws InterruptedException {
        int start = 0;
        while (getExcFlag() == 1 || start == 1) {
            setExcFlag(0);
            //偶数的数组长度,当start为1时,只有len/2-1个线程
            int latchNum = arr.length / 2 - (arr.length % 2 == 0 ? start : 0);
            System.out.println("线程数:" + latchNum);
            CountDownLatch countDownLatch = new CountDownLatch(latchNum);
            for (int i = start; i < arr.length - 1; i += 2) {
                pool.submit(new OddEvenSortTask(arr, i, countDownLatch));
            }
            countDownLatch.await();
            if (start == 0) {
                start = 1;
            } else {
                start = 0;
            }

        }
    }

    public static class ShellSortTask implements Runnable {
        int i = 0;
        int h = 0;
        CountDownLatch l;
        int arr[];

        public ShellSortTask(int i, int h, CountDownLatch l, int[] arr) {
            this.i = i;
            this.h = h;
            this.l = l;
            this.arr = arr;
        }

        @Override
        public void run() {
            if (arr[i] < arr[i - h]) {
                int tmp = arr[i];
                int j = i - h;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + h] = arr[j];
                    j -= h;
                }
                arr[j + h] = tmp;
            }
            l.countDown();
        }
    }


    public static void pShellSort(int[] arr) throws InterruptedException {
        int h = 1;
        CountDownLatch latch = null;
        //计算出最大的h值
        System.out.println(arr.length / 3);
        while (h <= arr.length / 3) {
            h = h * 3 + 1;
            System.out.println(h);
        }
        while (h > 0) {
            System.out.println("h=" + h);
            if (h >= 4) {
                latch = new CountDownLatch(arr.length - h);
            }
            for (int i = h; i < arr.length; i++) {
                //控制线程数量
                if (h >= 4) {
                    pool.execute(new ShellSortTask(i, h, latch, arr));
                } else {
                    //退化为传统的插入排序
                    if (arr[i] < arr[i - h]) {
                        int tmp = arr[i];
                        int j = i - h;
                        while (j >= 0 && arr[j] > tmp) {
                            arr[j + h] = arr[j];
                            j -= h;
                        }
                        arr[j + h] = tmp;
                    }
                }

            }
            //等待线程排序完成,进入下一此排序
            if (latch != null) {
                latch.await();
            }
            //计算出下一个h的值
            h = (h - 1) / 3;
        }
    }


    /**
     * 按从小到大的顺序交换数组
     *
     * @param a 传入的数组
     * @param b 传入的要交换的数b
     * @param c 传入的要交换的数c
     */
    public static void swap(int[] a, int b, int c) {
        int temp = 0;
        if (b < c) {
            if (a[b] > a[c]) {
                temp = a[b];
                a[b] = a[c];
                a[c] = temp;
            }
        }
    }

    /**
     * 打印数组
     *
     * @param array
     */
    public static void printArr(int[] array) {
        for (int c : array) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * 插入排序
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        int length = arr.length;
        int j, i, key;
//        int[] arr2 = {88, 3, 678, 51, 3, 7, 55};
        for (i = 1; i < length; i++) {
            //key为要准备插入的元素
            key = arr[i];
            j = i - 1;
            while (j >= 0 && arr[j] > key) {
//                arr[i]=arr[i-1]
                arr[j + 1] = arr[j];
                j--;
            }
            //找到合适的位置,插入key
            arr[j + 1] = key;
//            {3, 8, 678, 51, 3, 7, 55}
        }
    }

    /**
     * 希尔排序
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        //计算出最大的h值
        int h = 1;
        while (h <= arr.length / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            for (int i = h; i < arr.length; i++) {
                if (arr[i] < arr[i - h]) {
                    int tmp = arr[i];
                    int j = i - h;
                    while (j >= 0 && arr[j] > tmp) {
                        arr[j + h] = arr[j];
                        j -= h;
                    }
                    arr[j + h] = tmp;
                }
            }
            //计算出下一个h值
            h = (h - 1) / 3;
        }
    }

    static AtomicInteger atomicInteger = new AtomicInteger();

    public static void shellSort2(int[] arr) {
        //计算出最大的h值
        int h = 1;
        while (h <= arr.length / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
//            for (int i = 0; i < h; i++) {
//                for (int j = i+h; j < arr.length; j+=h) {

//                    int k=j-h;
//                    int tmp = arr[j];
//                    System.out.print("j:"+j+"k:"+k+",");
//                    while (k>=0 && arr[k]>tmp){
//                        arr[k + h] = arr[k];
//                        k -= h;
//                    }
//                    arr[k + h] = tmp;
//                }
//            }
//            4 0 8 4 12 8 1 0 2 1  8 4

            for (int i = h; i < arr.length; i++) {
                if (arr[i] < arr[i - h]) {
//                    4 0 5 1 6 2 7 5 8 4

                    int tmp = arr[i];
                    int j = i - h;
                    while (j >= 0 && arr[j] > tmp) {
                        arr[j + h] = arr[j];
                        j -= h;
                    }
                    arr[j + h] = tmp;
                }
            }
            //计算出下一个h值
            h = (h - 1) / 3;
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        int n = 9;
//        for (int i = n; i >0 ; i--) {
//            System.out.println(n/=2);
//        }

//        int h = 1;
//        int len = 9999;
//        while (h <= len / 3) {
//            h = h * 3 + 1;
//            System.out.println("h:" + h);
//        }
//        System.out.println(len / 3 * 3 + 1);
//        System.out.println("h:" + h);
//
//        int[] arr = new int[20];
////        List<Integer> collect = Arrays.stream(arr).boxed().collect(Collectors.toList());
//        Random r = new Random();
//        for (int i = 0; i < 20; i++) {
//            arr[i] = r.nextInt(100);
//        }
//        int[] arr2 = {88, 3, 678, 51, 3, 7, 55};
//        13 4 1
        int[] arr2 = {5, 52, 6, 3, 4};
        shellSort2(arr2);
//        insertSort(arr2);
//        pShellSort(arr2);
//        shellSort(arr2);
        printArr(arr2);
        System.out.println(atomicInteger.get());

//        System.out.println(JSON.toJSONString(collect));
//        batcherSort(arr);
//        oddEvenSort(arr);
//        pOddEvenSort(arr);
//        printArr(arr);

//        long s = System.currentTimeMillis();
//        oddEvenSort(arr);
//        long e = System.currentTimeMillis();
//        System.out.println("耗时1：" + (e - s));
//        long s1 = System.currentTimeMillis();
//        pOddEvenSort(arr);
//        long e1 = System.currentTimeMillis();
//        System.out.println("耗时2：" + (e1 - s1));
//      0  5 52 6  3  4
//      1  5 52 6  3  4    temp 52
//      2  5 52 52 3  4   temp 6
//         5 6  52 3  4
//      3  5 6  52 52 4  temp 3

//         5


    }

    public static void pShellSort2(int[] arr) throws InterruptedException {
        // 计算出最大的n值
        int h = 1;
        CountDownLatch lathc = null;
        while (h <= arr.length / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            System.out.println("h=" + h);

            lathc = new CountDownLatch(h);
            for (int x = 0; x < h; x++) {
                pool.submit(new ShellSortTask(x, h, lathc, arr));
            }
            lathc.await();
            // 计算下一个h值
            h = (h - 1) / 3;
        }
        pool.shutdown();
    }


}
