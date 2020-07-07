package com.ezfun.guess.spring.v2.aop;

import org.joda.time.TimeOfDay;

/**
 * @author SoySauce
 * @date 2020/6/30
 */
public class ServiceControlSubjectProxy implements IRequestable {
    private ISubject subject;

    @Override
    public void handler() {
        //每天午夜零点到次日6点间拦截
        TimeOfDay start = new TimeOfDay(0, 0, 0);
        TimeOfDay end = new TimeOfDay(5, 59, 59);
        TimeOfDay current = new TimeOfDay();
        if (current.isAfter(start) && current.isBefore(end)) {
            System.out.println("非有效时间");
        }
        Object original = subject.handler();
        System.out.println("ServiceControlSubjectProxy:" + original);
    }

    public ISubject getSubject() {
        return subject;
    }

    public void setSubject(ISubject subject) {
        this.subject = subject;
    }

    public ServiceControlSubjectProxy(ISubject subject) {
        this.subject = subject;
    }
}
