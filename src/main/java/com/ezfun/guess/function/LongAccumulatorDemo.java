package com.ezfun.guess.function;

import sun.misc.Contended;

import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * @author SoySauce
 * @date 2020/1/2
 */
@Contended
public class LongAccumulatorDemo {
    public static void main(String[] args) throws InterruptedException {
        LongAccumulator longAccumulator = new LongAccumulator(Long::min, -88);
        Thread[] ts = new Thread[1000];
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            ts[i] = new Thread(() -> longAccumulator.accumulate(finalI));
            ts[i].start();
        }
        for (int i = 0; i < 1000; i++) {
            ts[i].join();

        }
        System.out.println(longAccumulator.longValue());

    }
}
