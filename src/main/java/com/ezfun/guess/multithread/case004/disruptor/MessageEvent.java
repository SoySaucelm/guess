package com.ezfun.guess.multithread.case004.disruptor;

import lombok.Data;

/**
 * @author SoySauce
 * @date 2019/10/16
 */
@Data
public class MessageEvent<T> {
    private T message;
}
