package com.ezfun.guess.queue;

import com.ezfun.guess.queue.dto.AbstractSyncDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SoySauce
 * @date 2019/9/9
 */
@Component
@Slf4j
public class HandlerFac implements ApplicationContextAware {
    private Map<Class<?>, IBlockingQueueSyncHandler> handlers = new HashMap<>();

    public IBlockingQueueSyncHandler getHandler(Class<? extends AbstractSyncDto> clz) {
        return handlers.get(clz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IBlockingQueueSyncHandler> handlerMap = applicationContext.getBeansOfType(IBlockingQueueSyncHandler.class);
        if (!CollectionUtils.isEmpty(handlerMap)) {
            for (IBlockingQueueSyncHandler handler : handlerMap.values()) {
                ResolvableType resolvableType = ResolvableType.forInstance(handler);
                Class<?> resolve = resolvableType.getInterfaces()[0].getGeneric(0).resolve();
                handlers.put(resolve, handler);
                log.debug("cache sync handler  {} ", handler.getClass());
            }
        }
    }
}
