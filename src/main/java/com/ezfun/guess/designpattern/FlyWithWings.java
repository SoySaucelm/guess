package com.ezfun.guess.designpattern;

/**
 * @author SoySauce
 * @date 2019/8/20
 */
public class FlyWithWings implements FlyBehavior{
    @Override
    public void fly() {
        System.out.println("FlyWithWings....i'm flying");
    }
}
