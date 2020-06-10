/**
 *
 */
package com.ezfun.guess.queue;

import com.ezfun.guess.queue.dto.AbstractSyncDto;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * @author SoySauce
 * @Description 队列消费者
 */
public class QueueConsumer implements Runnable {

    private Logger logger = LoggerFactory.getLogger(QueueConsumer.class);
    private BlockingQueueLock<AbstractSyncDto> queue;
    private HandlerFac handlerFac;
    private BlockingQueueSyncPool pool;
    private int handlerRunnningInterval = -1;
    private int retryCount = 1;
    private static Map<String, String> property = Maps.newHashMap();

    static {
        init();
    }

    private static void init() {
        property.put("failure.retryCount", "3");
        property.put("handler.interval", "2000");
    }


    public QueueConsumer(BlockingQueueLock<AbstractSyncDto> queue, HandlerFac handlerFac, BlockingQueueSyncPool pool) {
        this.queue = queue;
        this.handlerFac = handlerFac;
        this.pool = pool;
        String retryCountStr = getProperty("failure.retryCount");
        if (StringUtils.hasText(retryCountStr))
            retryCount = Integer.parseInt(retryCountStr);
        String handlerRunnningIntervalStr = getProperty("handler.interval");
        if (StringUtils.hasText(handlerRunnningIntervalStr))
            this.handlerRunnningInterval = Integer.parseInt(handlerRunnningIntervalStr);

    }

    private String getProperty(String key) {
        return property.get(key);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void run() {
        AbstractSyncDto dto = null;

        while (true) {
            try {
                dto = queue.take();
                if (logger.isDebugEnabled())
                    logger.debug("get 1 {} from queue {}, surple size : {} , {}", dto.getClass(), queue.name, queue.size
                            , dto.getRetryCount() > 0 ? "retry " + dto.getRetryCount() + " time" : null);
                IBlockingQueueSyncHandler handler = handlerFac.getHandler(dto.getClass());
                if (handler != null) {
                    try {
                        handler.handle(dto);
                        logger.debug("handle success retrycount {} , for {} , queue {} suple size {}", dto.getRetryCount(), dto.toString(), queue.getName(), queue.getSize());
                    } catch (Exception e) {
                        if (
                                (e instanceof SSLHandshakeException || (null != e.getCause() && e.getCause() instanceof SSLHandshakeException))
                                        || (e instanceof SSLException || (null != e.getCause() && e.getCause() instanceof SSLException))
                                        || (e instanceof IOException || (null != e.getCause() && e.getCause() instanceof IOException))
//								|| ((e instanceof IOException && e.getMessage().indexOf("HTTP/1.1 429 Too Many Requests") >= 0) || (null != e.getCause() && e.getCause() instanceof IOException && e.getCause().getMessage().indexOf("HTTP/1.1 429 Too Many Requests") >= 0)) 
                                        || (e instanceof SocketException || (null != e.getCause() && e.getCause() instanceof SocketException))
                                        || ((e instanceof SocketTimeoutException && e.getMessage().indexOf("Read timed out") >= 0) || (null != e.getCause() && e.getCause() instanceof SocketTimeoutException && e.getCause().getMessage().indexOf("Read timed out") >= 0))) {
                            try {
                                if (dto.getRetryCount() < retryCount) {
                                    dto.addRetryCount();
                                    if (logger.isDebugEnabled())
                                        logger.debug("retry {} , for {} , queue {} suple size {}", dto.getRetryCount(), dto.toString(), queue.getName(), queue.getSize());
                                    pool.rePush(dto);
                                } else {
                                    logger.debug("======final fail retry:{}, url: {} , queue {} suple size {}", dto.getRetryCount(), dto.toString(), queue.getName(), queue.getSize());
                                }
                            } catch (PoolFullException pe) {
                                logger.error(pe.getMessage(), pe);
                            }
                        } else {
                            logger.error("sync error , dto {} cause: ", dto.toString(), e);
                        }
                    } finally {
                        System.out.println("finally.....");
                    }
                } else {
                    logger.warn("no suitable hanlder for obj : {}", dto.getClass());
                }
                if (handlerRunnningInterval > 0)
                    Thread.sleep(handlerRunnningInterval);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

    }
}
