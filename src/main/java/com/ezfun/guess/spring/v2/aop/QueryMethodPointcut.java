package com.ezfun.guess.spring.v2.aop;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author SoySauce
 * @date 2020/7/3
 */
public class QueryMethodPointcut extends StaticMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> clazz) {
        return method.getName().startsWith("get")
                && clazz.getPackage().getName().endsWith("aop");
    }
}
