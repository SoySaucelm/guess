package com.ezfun.guess.function;

/**
 * @author SoySauce
 * @date 2019/11/27
 */
public interface IHorse {
    void eat();
    default void run(){
        System.out.println("horse running");
    }
}
