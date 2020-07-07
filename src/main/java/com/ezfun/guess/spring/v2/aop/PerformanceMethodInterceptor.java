package com.ezfun.guess.spring.v2.aop;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StopWatch;

/**
 * @author SoySauce
 * @date 2020/7/3
 */
@Slf4j
public class PerformanceMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        StopWatch watch = new StopWatch();
        try {
            watch.start();
            return invocation.proceed();
        } finally {
            watch.stop();
            log.info("watch" + watch.toString());
        }
    }


}
