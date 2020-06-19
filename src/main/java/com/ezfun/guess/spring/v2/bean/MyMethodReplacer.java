package com.ezfun.guess.spring.v2.bean;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * @author SoySauce
 * @date 2020/6/14
 */
public class MyMethodReplacer implements MethodReplacer {
    @Override
    public Object reimplement(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("我替换了原来的方法");
        return null;
    }
}
