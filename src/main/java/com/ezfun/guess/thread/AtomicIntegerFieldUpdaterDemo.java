package com.ezfun.guess.thread;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author SoySauce
 * @date 2019/5/10
 */
@SuppressWarnings("all")
public class AtomicIntegerFieldUpdaterDemo {
    @Data
    static class Candidate {
        private int id;
        volatile int score;
    }

    private final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    public static AtomicInteger score = new AtomicInteger();

    public static void main(String[] args) {

        int pos = 10;
        int i = Integer.numberOfLeadingZeros(pos);
        int i2 = Integer.numberOfLeadingZeros(16);
        int index = (0x80000000 >>> i) ^ pos;
        int index2 = (Integer.MIN_VALUE >>> i) ^ pos;
        int index3 = (0b10000000000000000000000000000000 >>> i) ^ pos;
        int index4 = (-((Double)Math.pow(2,31)).intValue() >>> i) ^ pos;
        int index5 = (~((Double)Math.pow(2,31)).intValue() >>> i) ^ pos;
        System.out.println(index);
        System.out.println(index2);
        System.out.println(index3);
        System.out.println(index4);
        System.out.println(index5);
        System.out.println(~((Double)Math.pow(2,31)).intValue());
        System.out.println("------");
        System.out.println(~((Double)Math.pow(2,31)).intValue());
//        System.out.println(-((Double)Math.pow(2,31)).intValue());
        System.out.println(0x80000000);
        System.out.println(0b10000000000000000000000000000000);
        System.out.println(0b01111111111111111111111111111111);


        System.out.println(~Integer.MIN_VALUE);

//        System.out.println(i);
//        System.out.println(i2);
    }

    public static void main2(String[] args) throws InterruptedException {
        Candidate candidate = new Candidate();
        Thread[] t = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            t[i] = new Thread(() -> {
                if (Math.random() > 0.4) {
                    scoreUpdater.incrementAndGet(candidate);
                    score.incrementAndGet();
                }
            });
            t[i].start();
            t[i].join();
        }

        System.out.println("score:" + score);
        System.out.println("candidate:" + candidate.score);
    }
}
