package com.ezfun.guess.queue;

import com.ezfun.guess.queue.dto.Spider;
import org.springframework.stereotype.Component;

/**
 * @author SoySauce
 * @date 2019/9/9
 */
@Component
public class TestSyncHandler implements IBlockingQueueSyncHandler<Spider> {
    @Override
    public void handle(Spider obj) throws Exception {
        System.out.println("test handle......" + obj.getContent());
    }
}
