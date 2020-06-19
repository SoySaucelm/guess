package com.ezfun.guess.spring.v2.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SoySauce
 * @date 2020/6/14
 */
public class MyScope implements Scope {

    private final ThreadLocal<Map> threadScope = ThreadLocal.withInitial(HashMap::new);

    @Nullable
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map scope = threadScope.get();
        Object obj = scope.get(name);
        if (obj == null) {
            obj = objectFactory.getObject();
            scope.put(name, obj);
        }
        return obj;
    }

    @Override
    public Object remove(String name) {
        Map scope = threadScope.get();
        return scope.remove(name);
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
