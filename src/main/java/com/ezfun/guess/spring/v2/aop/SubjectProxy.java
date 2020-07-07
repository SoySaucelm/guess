package com.ezfun.guess.spring.v2.aop;

/**
 * @author SoySauce
 * @date 2020/6/30
 */
public class SubjectProxy implements ISubject {
    private ISubject subject;

    @Override
    public Object handler() {
        // do sth
        System.out.println("SubjectProxy start>>>>");
        Object obj = subject.handler();
        // do sth
        System.out.println("SubjectProxy>>>>" + obj);
        return "proxy:" + obj;
    }

    public ISubject getSubject() {
        return subject;
    }

    public void setSubject(ISubject subject) {
        this.subject = subject;
    }
}
