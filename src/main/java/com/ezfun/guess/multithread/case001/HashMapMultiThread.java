package com.ezfun.guess.multithread.case001;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SoySauce
 * @date 2019/9/10
 */
public class HashMapMultiThread {
    static Map<Integer, Integer> map = new HashMap(16);
//        static Map<Integer, Integer> map = new ConcurrentSkipListMap<>();
//    static Map<Integer, Integer> map =  new ConcurrentHashMap();
//    static Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap(16));
//    static Map<Integer, Integer> map = new Hashtable<>();


    static class AddThread implements Runnable {
        int start=0;

        public AddThread(int start) {
            this.start = start;
        }

        public AddThread() {
        }

        @Override
        public void run() {
            for (int j = start; j < 10000; j+=2) {
                map.put(j, j);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread(0));
        Thread t2 = new Thread(new AddThread(1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map.size());
    }
}
