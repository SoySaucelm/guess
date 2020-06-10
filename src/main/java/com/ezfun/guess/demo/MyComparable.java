package com.ezfun.guess.demo;

/**
 * @author SoySauce
 * @date 2019/4/28
 */
@FunctionalInterface
public interface MyComparable<T extends Comparable<T>>  {

    public int compare(T obj1, T obj2);
//    public static <T extends Comparable<T>> void testSort(List<T> list, MyComparable<T> comp) {
//        // sort the list
//    }
}