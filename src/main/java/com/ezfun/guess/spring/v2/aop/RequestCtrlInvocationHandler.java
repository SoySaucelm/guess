package com.ezfun.guess.spring.v2.aop;

import org.joda.time.TimeOfDay;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author SoySauce
 * @date 2020/6/30
 */
public class RequestCtrlInvocationHandler implements InvocationHandler {

    private Object target;

    public RequestCtrlInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("handler".equals(method.getName())) {
            //每天午夜零点到次日6点间拦截
            TimeOfDay start = new TimeOfDay(0, 0, 0);
            TimeOfDay end = new TimeOfDay(5, 59, 59);
            TimeOfDay current = new TimeOfDay();
            if (current.isAfter(start) && current.isBefore(end)) {
                System.out.println("非有效时间");
                return null;
            }
            Object invoke = method.invoke(target, args);
            System.out.println("RequestCtrlInvocationHandler>>>>>"+invoke);
            return invoke;
        }
        return proxy;
    }
}
