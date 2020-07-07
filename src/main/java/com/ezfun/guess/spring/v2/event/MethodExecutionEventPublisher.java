package com.ezfun.guess.spring.v2.event;

import com.ezfun.guess.spring.v2.event.sun.MethodExecutionStatus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SoySauce
 * @date 2020/6/18
 */
@Component
public class MethodExecutionEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    private List<MethodExecutionEventListener> listeners = new ArrayList<>();

    public void methodToMonitor() {
        MethodExecutionEvent beginEvent = new MethodExecutionEvent(this, "methodToMonitor", MethodExecutionStatus.BEGIN);
        this.eventPublisher.publishEvent(beginEvent);
        //执行具体业务逻辑
        System.out.println("MethodExecutionEventPublisher methodToMonitor...");
        MethodExecutionEvent endEvent = new MethodExecutionEvent(this, "methodToMonitor", MethodExecutionStatus.END);
        this.eventPublisher.publishEvent(endEvent);
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
