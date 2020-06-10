package com.ezfun.guess.multithread.case004.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author SoySauce
 * @date 2019/10/16
 */
public class MessageEventHandler implements EventHandler<MessageEvent> {
    @Override
    public void onEvent(MessageEvent event, long l, boolean b) throws Exception {
        System.out.println("eventHandler: " + event.getMessage());
    }
}
