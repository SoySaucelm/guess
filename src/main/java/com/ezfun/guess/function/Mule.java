package com.ezfun.guess.function;

import java.util.Comparator;

/**
 * @author SoySauce
 * @date 2019/11/27
 */
public class Mule implements IAnimal,IHorse,IDonkey{
    @Override
    public void eat() {
        System.out.println("Mule eating...");
    }

    @Override
    public void run() {
        IDonkey.super.run();
        IHorse.super.run();
    }

    public static void main(String[] args){
        Comparator<String> comparator = Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER);
        int compare = comparator.compare("saU", "ssc");
        System.out.println(compare);
        Mule mule = new Mule();
        mule.eat();
        mule.run();
        mule.breath();
    }
}
