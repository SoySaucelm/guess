package com.ezfun.guess.multithread.case002;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author SoySauce
 * @date 2019/10/11
 */
public class ConcurrentDemo {


    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("", "");
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        concurrentLinkedQueue.add("1");
        concurrentLinkedQueue.poll();
        CopyOnWriteArrayList<Object> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        ConcurrentSkipListMap<Object, Object> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        for (int i = 0; i < 30; i++) {
            concurrentSkipListMap.put(i,i);
        }
        for (Map.Entry<Object, Object> entry : concurrentSkipListMap.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
}
