package com.ezfun.guess.web;

import com.ezfun.guess.queue.BlockingQueueSyncPool;
import com.ezfun.guess.queue.PoolFullException;
import com.ezfun.guess.queue.dto.AbstractSyncDto;
import com.ezfun.guess.queue.dto.Spider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SoySauce
 * @date 2019/9/9
 */
@RestController
@RequestMapping("queue")
public class BlockingQueueController {
    @Autowired
    private BlockingQueueSyncPool pool;

    @RequestMapping("test")
    public void test(String content) throws InterruptedException, PoolFullException {
        AbstractSyncDto obj = new Spider();
        obj.setRetryCount(3);
        ((Spider) obj).setContent(content);
        pool.push(obj);
    }
}
