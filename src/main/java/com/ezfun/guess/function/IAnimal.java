package com.ezfun.guess.function;

/**
 * @author SoySauce
 * @date 2019/11/27
 */
public interface IAnimal {
    default void breath(){
        System.out.println("animal can breath");
    }
}
