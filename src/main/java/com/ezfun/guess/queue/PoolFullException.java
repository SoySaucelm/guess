package com.ezfun.guess.queue;

/**
 * @author SoySauce
 * @date 2019/9/9
 */
public class PoolFullException extends Exception {

    public PoolFullException(String messages, Throwable t) {
        super(messages, t);
    }
}
