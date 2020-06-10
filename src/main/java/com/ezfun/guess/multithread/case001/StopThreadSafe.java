package com.ezfun.guess.multithread.case001;

import lombok.Data;

/**
 * 终止线程
 * @author SoySauce
 * @date 2019/9/9
 */
public class StopThreadSafe {
    public static User u = new User();

    @Data
    public static class User {
        private int id;
        private String name;

        public User() {
            this.id = 0;
            this.name = "0";
        }

        @Override
        public String toString() {
            return "User [id=" + id + ",name=" + name + "]";
        }
    }

    public static class ChangeObjectThread extends Thread {
        volatile boolean stopFlag = false;

        public void stopMe() {
            stopFlag = true;
        }

        @Override
        public void run() {
            while (true) {
                if (stopFlag) {
//                    System.out.println("exit by stop me");
                    break;
                }
                synchronized (u) {
                    int v = (int) (System.currentTimeMillis() / 1000);
                    u.setId(v);
                    //do sth
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    u.setName(String.valueOf(v));
                }
                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    if (u.getId() != Integer.parseInt(u.getName())) {
                        System.out.println(u);
                    }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjectThread().start();
        while (true) {
            ChangeObjectThread t = new ChangeObjectThread();
            t.start();
            Thread.sleep(150);
            t.stopMe();
        }
    }
}
