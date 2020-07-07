package com.ezfun.guess.spring.v2.event.sun;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

/**
 * @author SoySauce
 * @date 2020/6/18
 */
public class MethodExecutionEventListenerImpl implements MethodExecutionEventListener {
    @Override
    public void onMethodBegin(MethodExecutionEvent event) {
        String methodName = event.getMethodName();
        System.out.println("start to execute the method:" + methodName);
    }

    @Override
    public void onMethodEnd(MethodExecutionEvent event) {
        String methodName = event.getMethodName();
        System.out.println("finished to execute the method:" + methodName);

    }

    public static void main(String[] args) {
        MethodExecutionEvent event = new MethodExecutionEvent("");
        event.setMethodName("abc");
        MethodExecutionEvent event2 = new MethodExecutionEvent("");
        event.setMethodName("abc2");
        ArrayList<MethodExecutionEvent> list = new ArrayList<>();
        list.add(event);
        list.add(event2);
        ArrayList<MethodExecutionEvent> methodExecutionEvents = new ArrayList<>(list);
        for (MethodExecutionEvent methodExecutionEvent : list) {
            methodExecutionEvent.setMethodName("ccc");
        }
        list.remove(0);
        System.out.println(JSON.toJSONString(list));
        System.out.println(JSON.toJSONString(methodExecutionEvents));


    }
}
