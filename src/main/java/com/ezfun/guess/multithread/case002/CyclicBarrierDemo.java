package com.ezfun.guess.multithread.case002;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏 是一种多线程并发控制实用工具,和countDownLatch(倒计时器)类似
 * 也可以实现线程间的计数等待,但是更加复杂且强大
 * <p>
 * CyclicBarrier(int parties 计数总数也就是参与的线程总数, Runnable barrierAction 当计数器一次计数完成后,系统会执行的动作) {
 *
 * @author SoySauce
 * @date 2019/9/26
 */
public class CyclicBarrierDemo {
    public static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclicBarrier;

        Soldier(CyclicBarrier cyclicBarrier, String soldierName) {
            this.cyclicBarrier = cyclicBarrier;
            this.soldier = soldierName;
        }

        @Override
        public void run() {
            //等待所有士兵到齐
            try {
                cyclicBarrier.await();
                doWork();
                //等待所有士兵完成工作
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void doWork() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier + "号:任务完成");
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
                System.out.println("司令:[士兵" + N + "个,任务完成!]");
            } else {
                System.out.println("司令:[士兵" + N + "个,集合完毕!]");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Object> list = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        list.set(0,66);
        System.out.println(JSON.toJSONString(list));


        if (true){
            return;
        }


        final int N = 10;
        Thread[] allSoldier = new Thread[N];

        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRun(false, N));
        //设置屏障点
        System.out.println("集合队伍");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵" + i + "号报到!");
            allSoldier[i] = new Thread(new Soldier(cyclicBarrier, "士兵" + i));
            allSoldier[i].start();
            if (i==5){
                allSoldier[0].interrupt();
            }
        }
    }


}
