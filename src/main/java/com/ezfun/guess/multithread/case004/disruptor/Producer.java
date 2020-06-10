package com.ezfun.guess.multithread.case004.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author SoySauce
 * @date 2019/10/12
 */
public class Producer {
    /**
     * 环形缓存区
     */
    private final RingBuffer<PCData> ringBuffer;

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 将产生的数据推入缓冲区
     *
     * @param bb ByteBuffer
     */
    public void pushData(ByteBuffer bb) {
        // 抓住下一个序列 grab the next sequence 得到一个可用的序列号
        long sequence = ringBuffer.next();
        try {
            //通过序列号,取得下一个空闲可用的PCData
            PCData event = ringBuffer.get(sequence);
            //fill with data 填充数据  将PCData的数据设为期望值,这个值最终会传递给消费者
            event.setValue(bb.getLong(0));
        } finally {
            //进行数据发布,只有发布后的数据才会真正被消费者看见
            ringBuffer.publish(sequence);
        }


    }
}
