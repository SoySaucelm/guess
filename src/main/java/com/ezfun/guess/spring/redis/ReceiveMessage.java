package com.ezfun.guess.spring.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DefaultMessage;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author SoySauce
 * @date 2019/8/16
 */

@Slf4j
@Component
public class ReceiveMessage implements MessageListener {

    //    /**
//     * JmsListener onMessage
//     *
//     * @param message
//     */
//    @Override
//    @JmsListener(destination = "WB_GA2.PUBSUB.RECEIVE.Q")
//    public void onMessage(Message message) {
//
//    }
    @Autowired
    private RedisTemplate redisTemplate;

    public void handleMessage(Object msg, String bytes) {
        log.info("handle msg:{} channel:{}", msg,bytes);
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        try {
            RedisSerializer<String> valueSerializer = redisTemplate.getStringSerializer();
            String deserialize = valueSerializer.deserialize(message.getBody());
            System.out.println(deserialize);
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            log.info("mq订阅消息 message:{},body:{}", message, body);
            DefaultMessage textMessage = (DefaultMessage) message;
            String result = new String(textMessage.getBody());
            log.info("mq订阅消息:{}", result);
        } catch (Exception e) {
            log.info("mq订阅消息 error:{}", e);
        }
    }
}
