package com.ezfun.guess.function;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author SoySauce
 * @date 2019/11/29
 */
public class InstanceMethodRef {
    public static void main(String[] args){
        List<User> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(new User((long) i, "i'm jack", "love movie", 17));
        }
        list.stream().map(User::getName).forEach(System.out::println);
    }
}
