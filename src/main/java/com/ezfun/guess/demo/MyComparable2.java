package com.ezfun.guess.demo;

/**
 * @author SoySauce
 * @date 2019/4/28
 */
public interface MyComparable2 {
    public <T extends Comparable<T>> int compare(T obj1, T obj2);
}
