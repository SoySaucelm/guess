package com.ezfun.guess.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SoySauce
 * @date 2019/11/21
 */
@Slf4j
public class ConcurrencyTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(500);
        AtomicInteger errorCount = new AtomicInteger();
//        String url = "http://10.67.200.207:8002/v1/api/coupon/queryCouponInfo?isUse=0&issuerAccountId=11&userId=31938002&sign=60ba9a80290b9f037412f072d3d4e661";
//        String url = "http://10.66.28.226:8881/v1/api/coupon/queryCouponInfo?issuerAccountId=600001&userId=888&sign=9831663670f34dcd7b9e3018d66b4a41";
//        String url = "http://localhost:8702/addService-services/api/orderSync/upgradeOrder";
        String url = "http://localhost:8002/addService-services/api/orderSync/wifiOrder";
//        String url = "http://10.67.200.207:9001/api/v1/sysNotice/";
        String content = "{\n" +
                "            \"orderType\": \"WIFI\",\n" +
                "            \"orderSubType\": \"GH_ONSALE\",\n" +
                "            \"orderNo\": \"123456\",\n" +
                "            \"orderStatus\": \"2\",\n" +
                "            \"tradeOrderNo\": \"6666\",\n" +
                "            \"orderOptStatus\": \"ADD\",\n" +
                "            \"userId\": \"888888\",\n" +
                "            \"point\": \"200\",\n" +
                "            \"oldOrderStatus\": \"1\",\n" +
                "            \"subOrderNo\": \"2345\",\n" +
                "            \"price\": \"200.00\",\n" +
                "            \"ticketNo\":\"2222\",\n" +
                "            \"winStatus\":\"2\",\n" +
                "            \"wifiInfos\": [\n" +
                "                {\n" +
                "                    \"depAirportCode\": \"CAN\",\n" +
                "                    \"flightNo\": \"MU666\",\n" +
                "                    \"wifiName\": \"测试\",\n" +
                "                    \"arrAirportCode\": \"SHA\",\n" +
                "                    \"depDate\": \"2020-02-01\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"ffpNo\": \"55555\",\n" +
                "            \"createDate\": \"2020-02-01\"\n" +
                "        }";
        JSONObject jsonObject = JSON.parseObject(content);
        int num = 3000;
        CountDownLatch latch = new CountDownLatch(num);
        long start = System.currentTimeMillis();
        int x = 0;
        for (int i = 0; i < num; i++) {
            x++;

            pool.submit(() -> {
                try {
                    long s = System.currentTimeMillis();
                    jsonObject.put("orderNo", UUID.randomUUID());
                    JSONObject res = RestUtil.restPost(url,jsonObject, MediaType.APPLICATION_JSON, JSONObject.class);
//                    String content = RestUtil.restGet(url);
//                    JSONObject res = JSON.parseObject(content);
                    long e = System.currentTimeMillis();
                    if (!res.getBoolean("success")) {
                        long l = e - s;
                        log.error("error:>>>>>>>>>>>>>>>>>>>:{}:{}", res, l);
//                        if (l > 20000) {
//
//                        }
                        errorCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    errorCount.incrementAndGet();
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("---------------------");
        System.out.println("数量:" + num);
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
        System.out.println("错误次数:" + errorCount);

    }

}
