package com.ezfun.guess.stream;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SoySauce
 * @date 2020/7/3
 */
public class Sort {
    @Data
    static class User {
        private int age;
        private int level;
    }

    static String data = "[{\n" +
            "  \"age\":10,\n" +
            "  \"level\":2\n" +
            "},{\n" +
            "  \"age\":9,\n" +
            "  \"level\":5\n" +
            "},\n" +
            "  {\n" +
            "    \"age\":9,\n" +
            "    \"level\":3\n" +
            "  },\n" +
            "  {\n" +
            "    \"age\":9,\n" +
            "    \"level\":3\n" +
            "  }\n" +
            "]";

    public static void main(String[] args){
        List<User> users = JSONArray.parseArray(data, User.class);
        ArrayList<User> list = Lists.newArrayList();
        User user;
        for (int i = 1000000; i >0 ; i--) {
            user=new User();
            user.setAge(i);
            user.setLevel(i);
            list.add(user);
        }
        long l1 = System.currentTimeMillis();
        List<User> collect = list.stream().sorted(Comparator.comparing(User::getAge,
                Comparator.reverseOrder()).thenComparing(User::getLevel,
                Comparator.reverseOrder()))
                .collect(Collectors.toList());
        long l2 = System.currentTimeMillis();
        List<User> collect2 = list.stream().sorted(Comparator.comparing(User::getAge).thenComparing
                (User::getLevel).reversed())
                .collect(Collectors.toList());
        long l3 = System.currentTimeMillis();

        System.out.println(l2-l1);
        System.out.println(l3-l2);
    }
}
