package com.ezfun.guess.spring.v2.event.sun;

import java.util.EventListener;

/**
 * @author SoySauce
 * @date 2020/6/18
 */
public interface MethodExecutionEventListener extends EventListener {
    /**
     * 处理方法开始执行的时候发布的MethodExecutionEvent 事件
     * @param event
     */
    void onMethodBegin(MethodExecutionEvent event);

    /**
     * 处理方法执行将结束时候发布的MethodExecutionEvent 事件
     * @param event
     */
    void onMethodEnd(MethodExecutionEvent event);
}
