package com.ezfun.guess.spring.v2.aop;

/**
 * @author SoySauce
 * @date 2020/7/1
 */
public class TargetCaller {
    private TargetObject target;

    public void callMethod() {
        target.method();
        System.out.println("TargetCaller >>>>>>>>>");
    }

    public void setTarget(TargetObject target) {
        this.target = target;
    }
}
