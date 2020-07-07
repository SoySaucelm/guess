package com.ezfun.guess.spring.v2.aop;

/**
 * @author SoySauce
 * @date 2020/6/30
 */
public class IRequestableImpl implements IRequestable{
    @Override
    public void handler() {
        System.out.println("request processed in IRequestableImpl");
    }
}
