package com.ezfun.guess.spring.v2.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author SoySauce
 * @date 2020/6/18
 */
@Component
public class MethodExecutionEventListener implements ApplicationListener {


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MethodExecutionEvent){
            //执行处理逻辑
            System.out.println("onApplicationEvent Do Sth...");
        }
    }
}
