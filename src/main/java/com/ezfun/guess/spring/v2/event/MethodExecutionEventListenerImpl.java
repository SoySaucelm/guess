package com.ezfun.guess.spring.v2.event;

/**
 * @author SoySauce
 * @date 2020/6/18
 */
public class MethodExecutionEventListenerImpl implements MethodExecutionEventListener {
    @Override
    public void onMethodBegin(MethodExecutionEvent event) {
        String methodName = event.getMethodName();
        System.out.println("start to execute the method:"+methodName);
    }

    @Override
    public void onMethodEnd(MethodExecutionEvent event) {
        String methodName = event.getMethodName();
        System.out.println("finished to execute the method:"+methodName);

    }
}
