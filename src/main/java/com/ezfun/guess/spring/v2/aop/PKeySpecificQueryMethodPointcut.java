package com.ezfun.guess.spring.v2.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author SoySauce
 * @date 2020/7/3
 */
public class PKeySpecificQueryMethodPointcut extends DynamicMethodMatcherPointcut {
    @Override
    public boolean matches(Method method, Class<?> clazz, Object... args) {
        if (method.getName().startsWith("get")
                && clazz.getPackage().getName().endsWith("aop")) {
            if (!ArrayUtils.isEmpty(args)) {
                return StringUtils.equals("1", args[0].toString());
            }
        }
        return false;
    }
}
