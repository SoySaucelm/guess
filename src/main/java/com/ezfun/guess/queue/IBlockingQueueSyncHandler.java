package com.ezfun.guess.queue;

import com.ezfun.guess.queue.dto.AbstractSyncDto;

/**
 * @author SoySauce
 * @date 2019/9/9
 */
public interface IBlockingQueueSyncHandler<T extends AbstractSyncDto> {

    void handle(T obj) throws Exception;
}
