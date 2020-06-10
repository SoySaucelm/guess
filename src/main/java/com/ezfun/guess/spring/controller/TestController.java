package com.ezfun.guess.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SoySauce
 * @date 2020/1/14
 */
@RestController
@EnableScheduling
public class TestController {
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("send")
    //第一次执行之前延后10秒钟；后续每隔5秒执行1次
    @Scheduled(fixedRate = 5000, initialDelay = 1000)
    public String send() {
//        System.out.println(redisTemplate);
//        redisTemplate.opsForValue().set("idea","hello,idea");
//        redisTemplate.convertAndSend("test", "hello!");
//        redisTemplate.convertAndSend("name", "world!");
        return "abc";
    }

}
