package com.ezfun.guess.multithread.case004.disruptor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * 官方报告,Disruptor性能要比BlockingQueue至少高一个数量级以上.
 *
 * @author SoySauce
 * @date 2019/10/12
 */
public class NewDisruptorTest {


    public static void main(String[] args) throws InterruptedException {
        ThreadFactory basicThreadFactory = new BasicThreadFactory.Builder().namingPattern("环形队列测试").daemon(true).build();
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("环形队列测试").setDaemon(true).build();
        //设置缓冲区大小为1024 (必须为2的整数次方)
        int ringBufferSize = 1024;
        //创建disruptor对象.它封装了整个disruptor库的使用,提供了一些便捷api
        //当有新数据产生消费者如何知道(监控缓冲区信息),主要有以下几种策略: 这些策略由WaitStrategy接口封装
//      BlockingWaitStrategy 默认的策略 和BlockingQueue非常类似,都使用锁和条件(Condition)进行数据的监控和线程的唤醒.因为涉及到线程切换 该策略是最省CPU,
//      但高并发下性能最糟糕的一种等待策略
        WaitStrategy blockingWaitStrategy = new BlockingWaitStrategy();
        WaitStrategy sleepingWaitStrategy = new SleepingWaitStrategy();
        WaitStrategy yieldingWaitStrategy = new YieldingWaitStrategy();
        WaitStrategy busySpinWaitStrategy = new BusySpinWaitStrategy();
        //        这里用的是单一生成者，如果是多生成者的话是另一种模式，自己的类实现WorkHandler接口
        Disruptor<MessageEvent> disruptorSingle = new Disruptor<>(new MessageEventFactory(), ringBufferSize,
                basicThreadFactory, ProducerType.SINGLE, blockingWaitStrategy);
        //多生产者模式
//        Disruptor<MessageEvent> disruptor = new Disruptor<>(new MessageEventFactory(), ringBufferSize,
//                basicThreadFactory, ProducerType.MULTI, blockingWaitStrategy);
        //设置4个用于处理数据的消费者,系统会为将每一个消费者实例映射到一个线程中.
        disruptorSingle.handleEventsWithWorkerPool(
                new MessageWorkHandler(),
                new MessageWorkHandler(),
                new MessageWorkHandler(),
                new MessageWorkHandler()
        );

        disruptorSingle.handleEventsWith(new MessageEventHandler());
        disruptorSingle.setDefaultExceptionHandler(new ExceptionHandler<MessageEvent>() {
            @Override
            public void handleEventException(Throwable throwable, long l, MessageEvent event) {
                throwable.printStackTrace();
            }

            @Override
            public void handleOnStartException(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void handleOnShutdownException(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        //启动并初始化disruptor系统
        disruptorSingle.start();
        //获取环形缓冲区
        RingBuffer<MessageEvent> ringBuffer = disruptorSingle.getRingBuffer();
        //创建一个生产者
        MessageEventProducer producer = new MessageEventProducer(ringBuffer);
        String msg = "Hi Disruptor!";
        //无限循环向缓冲区中存入数据
        for (long i = 0; true; i++) {
            producer.push(msg + ":" + i);
            Thread.sleep(100);
        }

    }
}
