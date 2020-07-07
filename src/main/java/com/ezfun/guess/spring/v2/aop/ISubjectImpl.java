package com.ezfun.guess.spring.v2.aop;

/**
 * @author SoySauce
 * @date 2020/6/30
 */
public class ISubjectImpl implements ISubject{
    @Override
    public Object handler() {
        System.out.println("handler processed in ISubjectImpl");
        return "from ISubjectImpl";
    }
}
