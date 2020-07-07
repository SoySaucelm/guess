package com.ezfun.guess.spring.v2.aop;

import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.cglib.proxy.Enhancer;

/**
 * @author SoySauce
 * @date 2020/6/30
 */
public class Test {



    public static void main(String[] args) {



        /*ISubject target = new ISubjectImpl();
        ISubject proxy = new ServiceControlSubjectProxy(target);
        Object handler = proxy.handler();
        System.out.println(handler);*/

/*        ISubject subject = (ISubject)Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new Class[]{ISubject.class},
                new RequestCtrlInvocationHandler(new ISubjectImpl()));
        subject.handler();
        IRequestable requestAble = (IRequestable)Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new
                        Class[]{IRequestable.class},
                new RequestCtrlInvocationHandler(new IRequestableImpl()));
        requestAble.handler();*/

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Requestable.class);
        enhancer.setCallback(new RequestCtrlCallback());
        Requestable proxy = (Requestable) enhancer.create();
        proxy.handler();

        ControlFlowPointcut flowPointcut = new ControlFlowPointcut(TargetCaller.class);


    }

}
