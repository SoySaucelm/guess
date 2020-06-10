package com.ezfun.guess.multithread.case004.jdkfuture;

import java.util.concurrent.*;

/**
 * @author SoySauce
 * @date 2019/10/14
 */
public class FutureMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new RealData("A"));
        ExecutorService executor = Executors.newFixedThreadPool(1);
        //执行FutureTask 相当于之前的client.request()
        executor.submit(futureTask);

        System.out.println("请求完毕!");
        try {
            //使用sleep代替对其它业务逻辑的处理
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //相当于之前data.getResult()
        //取得call()返回值,如果此时没有执行完成,则已然会等待
        System.out.println("数据: " + futureTask.get());
        System.out.println("over...");
    }
}
