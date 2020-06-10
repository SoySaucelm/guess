package com.ezfun.guess.multithread.case004.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * @author SoySauce
 * @date 2019/10/16
 */
public class MessageEventProducer {
    private RingBuffer<MessageEvent> ringBuffer;

    public MessageEventProducer(RingBuffer<MessageEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void push(Object msg) {
        EventTranslatorOneArg<MessageEvent, Object> translator = new EventTranslatorOneArg<MessageEvent,
                Object>() {
            @Override
            public void translateTo(MessageEvent event, long l, Object o) {
                event.setMessage(o);
            }
        };
//        EventTranslatorOneArg<MessageEvent, Object> translator = (event, l, o) -> event.setMessage(o);
        ringBuffer.publishEvent(translator, msg);
    }
}
