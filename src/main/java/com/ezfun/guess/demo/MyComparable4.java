package com.ezfun.guess.demo;

/**
 * @author SoySauce
 * @date 2019/4/28
 */
@FunctionalInterface
public interface MyComparable4 extends Comparable {

//   static <T extends Comparable<T>> int compareTo(T t, T t1) {
//        return 0;
//    }

    @Override
    public int compareTo(Object o);
}
