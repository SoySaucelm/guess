package com.ezfun.guess.designpattern.observer;

/**
 * DisplayElement接口只包含了一个方法，
 * 也就是display()。当布告板需要显示时，
 * 调用此方法。
 *
 * @author SoySauce
 * @date 2019/8/20
 */
public interface DisplayElement {
    /**
     * 我们也为布告板建立一个
     * 共同的接口。布告板只需要
     * 实现display()方法。
     * 显示
     */
    void display();
}
