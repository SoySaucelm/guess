package com.ezfun.guess.designpattern;

/**
 * @author SoySauce
 * @date 2019/8/20
 */
public class FlyWithNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("FlyWithNoWay....i can't fly...");
    }
}
