package com.ezfun.guess.designpattern.observer;

/**
 * @author SoySauce
 * @date 2020/1/20
 */
public class OtherObserver implements MyObserver, DisplayElement {
    MySubject subject;
    /**
     * 温度气温
     */
    private float temperature;
    /**
     * 湿度
     */
    private float humidity;
    /**
     * 气压
     */
    private float pressure;

    public OtherObserver(MySubject subject) {
        this.subject = subject;
        subject.registerObserver(this);
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }

    //    @Override
//    public void display() {
//        System.out.println(String.format("OtherObserver~~~~~temperature%s%s%s", ((WeatherData)subject).temperature,
//                ((WeatherData)subject).humidity, ((WeatherData)subject).pressure));
//    }
    @Override
    public void display() {
        subject.removeObserver(this);
        System.out.println(String.format("OtherObserver~~~~~temperature%s%s%s", temperature, humidity, pressure));

    }
}
