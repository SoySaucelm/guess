package com.ezfun.guess.multithread.case004.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author SoySauce
 * @date 2019/10/16
 */
public class MessageEventFactory implements EventFactory<MessageEvent> {
    @Override
    public MessageEvent newInstance() {
        return new MessageEvent();
    }
}
