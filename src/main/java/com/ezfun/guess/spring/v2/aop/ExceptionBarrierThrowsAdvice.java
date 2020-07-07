package com.ezfun.guess.spring.v2.aop;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.mail.javamail.JavaMailSender;

import java.lang.reflect.Method;

/**
 * @author SoySauce
 * @date 2020/7/3
 */
public class ExceptionBarrierThrowsAdvice implements ThrowsAdvice {
    private JavaMailSender mailSender;
    private String[] receiptions;

    public void afterThrowing(Throwable t) {
        //普通异常处理逻辑
    }

    public void afterThrowing(RuntimeException e) {
        //运行时异常处理逻辑
    }

    public void afterThrowing(Method m, Object[] args, Object obj, RuntimeException e) {
        //处理应用程序生成的异常
        final String exceptionMessage = ExceptionUtils.getMessage(e);
        System.out.println(exceptionMessage);
//        mailSender.send(new SimpleMailMessage());

    }

}
