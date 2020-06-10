package com.ezfun.guess.function;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author SoySauce
 * @date 2019/11/29
 */
public class BadMethodRef {
    public static void main(String[] args) {
        List<Double> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(Double.valueOf(i));
        }
//        public String toString()
//        public static String toString(double d)
        //同时存在同名的实例方法和静态方法 编译器不知道该使用哪个方法
//        list.stream().map(Double::toString).forEach(System.out::println);
    }
}
