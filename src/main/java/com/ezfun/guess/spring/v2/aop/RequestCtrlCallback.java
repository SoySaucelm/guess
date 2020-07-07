package com.ezfun.guess.spring.v2.aop;


import org.joda.time.TimeOfDay;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author SoySauce
 * @date 2020/6/30
 */
public class RequestCtrlCallback implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if ("handler".equals(method.getName())) {
            //每天午夜零点到次日6点间拦截
            TimeOfDay start = new TimeOfDay(0, 0, 0);
            TimeOfDay end = new TimeOfDay(5, 59, 59);
            TimeOfDay current = new TimeOfDay();
            if (current.isAfter(start) && current.isBefore(end)) {
                System.out.println("非有效时间");
                return null;
            }
            Object origin = methodProxy.invokeSuper(obj, args);
            System.out.println("RequestCtrlInvocationHandler>>>>>" + origin);
            return origin;
        }
        return null;
    }
}
