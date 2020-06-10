package com.ezfun.guess.designpattern.observer;

/**
 * @author SoySauce
 * @date 2020/1/20
 */
public interface MyObserver {
    void update(float temperature, float humidity, float pressure);
}
