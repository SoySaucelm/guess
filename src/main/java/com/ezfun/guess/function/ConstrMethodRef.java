package com.ezfun.guess.function;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author SoySauce
 * @date 2019/11/29
 */
public class ConstrMethodRef {
    interface UserFactory<T extends  User>{
        T create(Long id, String name);
    }

    static UserFactory<User> userFactory = User::new;

    public static void main(String[] args){
        List<User> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(userFactory.create((long) i,"jack "+i));
        }
        list.stream().map(User::getName).forEach(System.out::println);
    }


}
