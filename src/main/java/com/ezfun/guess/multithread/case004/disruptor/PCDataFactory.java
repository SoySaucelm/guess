package com.ezfun.guess.multithread.case004.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author SoySauce
 * @date 2019/10/12
 */
public class PCDataFactory implements EventFactory<PCData> {
    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
