package com.ezfun.guess.designpattern.observer;

/**
 * 这是主题接口，对象使用此
 * 接口注册为观察者，或者把
 * 自己从观察者中删除
 * 每个主题可以有
 * 许多观察者
 *
 * @author SoySauce
 * @date 2019/8/20
 */
public interface MySubject {

    //registerObserver,removeObserver这两个方法都需要一个观察者作为变量，
    //该观察者是用来注册或被删除的。

    /**
     * 注册观察者
     *
     * @param o
     */
    public void registerObserver(MyObserver o);

    /**
     * 删除观察者
     *
     * @param o
     */
    public void removeObserver(MyObserver o);

    /**
     * 当主题状态改变时， 这个方法会被
     * 调用，以通知所有的观察者。
     */
    public void notifyObserver();
}
