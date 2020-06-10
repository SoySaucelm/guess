package com.ezfun.guess.multithread.case004.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * @author SoySauce
 * @date 2019/10/16
 */
public class MessageWorkHandler implements WorkHandler<MessageEvent> {
    @Override
    public void onEvent(MessageEvent event) throws Exception {
        System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().getId() + ":Event:--" + event
                .getMessage());
    }
}
