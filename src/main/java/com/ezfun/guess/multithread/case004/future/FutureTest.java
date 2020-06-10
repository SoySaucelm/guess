package com.ezfun.guess.multithread.case004.future;

import java.util.concurrent.Executors;

/**
 * @author SoySauce
 * @date 2019/10/14
 */
public class FutureTest {
    public static void main(String[] args) {
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕");
        System.out.println("other sth start...");
        try {
            //使用sleep代替对其它业务逻辑的处理
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("other sth end...");
        //使用真实数据
        System.out.println("数据: " + data.getResult());
    }
}
