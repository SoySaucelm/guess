package com.ezfun.guess.function;

/**
 * @author SoySauce
 * @date 2019/11/27
 */
public interface IDonkey {
    void eat();
    default void run(){
        System.out.println("Donkey running");
    }
}
