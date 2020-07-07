package com.ezfun.guess.spring.v2.aop;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.core.io.Resource;

import java.lang.reflect.Method;

/**
 * @author SoySauce
 * @date 2020/7/3
 */
public class ResourceSetupBeforeAdvice implements MethodBeforeAdvice {
    private Resource resource;

    public ResourceSetupBeforeAdvice(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void before(Method method, Object[] objects, Object target) throws Throwable {
        if (!resource.exists()) {
            FileUtils.forceMkdir(resource.getFile());
        }
    }
}
