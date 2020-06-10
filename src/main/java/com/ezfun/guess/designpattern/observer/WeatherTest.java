package com.ezfun.guess.designpattern.observer;

/**
 * @author SoySauce
 * @date 2020/1/20
 */
public class WeatherTest {

    public static void main(String[] args) {
        MySubject data = new WeatherData();
        ScreenObserver screenObserver = new ScreenObserver(data);
        OtherObserver otherObserver = new OtherObserver(data);
        ((WeatherData) data).setHumidity(22f);
//        data.removeObserver(otherObserver);
        ((WeatherData) data).setMeasurements(1,2,3);
//        ((WeatherData) data).setHumidity(66f);
    }
}
