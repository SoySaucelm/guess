package com.ezfun.guess.designpattern.observer;

/**
 * @author SoySauce
 * @date 2020/1/20
 */
public class ScreenObserver implements MyObserver {
    MySubject subject;

    public ScreenObserver(MySubject subject) {
        this.subject = subject;
        subject.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        System.out.println(String.format("ScreenObserver>>>>>temperature%s%s%s", temperature, humidity, pressure));
    }
}
