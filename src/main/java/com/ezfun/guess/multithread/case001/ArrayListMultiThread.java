package com.ezfun.guess.multithread.case001;

import java.util.List;
import java.util.Vector;

/**
 * @author SoySauce
 * @date 2019/9/10
 */
public class ArrayListMultiThread {
    //    static ArrayList<Integer> list = new ArrayList<>(10);
//    static List<Integer> list = new CopyOnWriteArrayList<>();
//    static Set<Integer> list =  new ConcurrentSkipListSet();
//    static List<Integer> list = Collections.synchronizedList(new ArrayList<>());
    static List<Integer> list = new Vector<>();


    static class AddThread implements Runnable {
        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                list.add(j);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(list.size());
    }
}
