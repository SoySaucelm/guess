package com.ezfun.guess.spring.v2.aop;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author SoySauce
 * @date 2020/7/3
 */
public class TaskExecutionAfterReturningAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method m, Object[] args, Object target) throws Throwable {
        Class<?> clazz = target.getClass();
        String name = clazz.getName();
        // insert name
    }
}
