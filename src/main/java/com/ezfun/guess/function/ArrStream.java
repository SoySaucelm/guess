package com.ezfun.guess.function;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.*;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

/**
 * @author SoySauce
 * @date 2019/11/29
 */
public class ArrStream {
    static int[] arr = {1, 5, 3, 4, 6, 7, 8, 9};

    @Data
    static class User{
        private String name;
        private int age;
    }
    public static void main(String[] args){
        ArrayList<User> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(i);
            user.setName("name:"+i);
            list.add(user);
        }
        List<Integer> collect = list.stream().map(obj -> obj.getAge() + 1000).sorted(Comparator.comparingInt(o -> o)).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect));
        list.forEach(obj->obj.setAge(1000));
        System.out.println(JSON.toJSONString(list));

    }

    public static void main2(String[] args) {
//        Arrays.stream(arr).forEach(new IntConsumer() {
//            @Override
//            public void accept(int value) {
//                System.out.println(value);
//            }
//        });
        IntConsumer outPrint = System.out::println;
        IntConsumer errOutPrint = System.err::println;
//        Arrays.stream(arr).forEach(outPrint.andThen(errOutPrint));
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(2));
        }
        Arrays.setAll(arr, new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {
//                System.out.println(operand);
                return random.nextInt(2);
            }
        });
//        CompletionStage stage = new CompletionStage() {
//
//        };
//        stage.thenAccept(x->square(x)).thenAccept().thenRun()

//        Arrays.stream(arr).forEach(System.out::println);

    }
}
