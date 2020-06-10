package com.ezfun.guess.multithread.case004.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * @author SoySauce
 * @date 2019/10/12
 */
public class Consumer implements WorkHandler<PCData> {

    @Override
    public void onEvent(PCData event) throws Exception {
        System.out.println(Thread.currentThread().getId() + ":Event:--" + event.getValue() * event.getValue() + "--");
    }

}
