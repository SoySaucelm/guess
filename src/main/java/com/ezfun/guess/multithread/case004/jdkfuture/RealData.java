package com.ezfun.guess.multithread.case004.jdkfuture;

import java.util.concurrent.Callable;

/**
 * 最终需要使用的数据模型.使用sleep模拟
 *
 * @author SoySauce
 * @date 2019/10/14
 */
public class RealData implements Callable<String> {
    private final String param;

    public RealData(String param) {
        this.param = param;
    }

    @Override
    public String call() throws Exception {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(param);
        }
        System.out.println("拼命加载中...");
        //使用sleep模拟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("加载完毕!!!");
        return sb.toString();
    }
}
