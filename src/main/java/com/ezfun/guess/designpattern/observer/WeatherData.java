package com.ezfun.guess.designpattern.observer;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author SoySauce
 * @date 2020/1/20
 */
public class WeatherData implements MySubject {

    private List<MyObserver> observers;

    public WeatherData() {
        this.observers = Lists.newArrayList();
    }

    /**
     * 温度气温
     */
    public float temperature;
    /**
     * 湿度
     */
    public float humidity;
    /**
     * 气压
     */
    public float pressure;

    /**
     * 测量值已更改
     */
    public void measurementsChanged() {
        notifyObserver();
    }


    @Override
    public void notifyObserver() {
//        observers.forEach(observer -> observer.update(temperature, humidity, pressure));
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(temperature, humidity, pressure);
        }
    }

    @Override
    public void registerObserver(MyObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(MyObserver observer) {
        observers.remove(observer);
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
        measurementsChanged();
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
        measurementsChanged();
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
        measurementsChanged();
    }
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}
