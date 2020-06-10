package com.ezfun.guess.multithread.case004.other;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author SoySauce
 * @date 2019/10/16
 */
public class StreamDemo {
    public static void sort() {
        System.out.println(16 * 0.75);
        Map<String, Object> map = new HashMap<>(8);
        map.put("name", "ZK");
        map.put("age", 13);
        Map<String, Object> map2 = new HashMap<>(2);
        map2.put("name", "ZA");
        map2.put("age", 15);
        Map<String, Object> map3 = new HashMap<>(16);
        map3.put("name", "CX");
        map3.put("age", 20);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("name", "CX");
        map4.put("age", 18);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        list.add(map2);
        list.add(map3);
        list.add(map4);
//        先按name 字母顺序正序，然后按age 数字倒序
        List<Map<String, Object>> collect = list.stream().sorted(Comparator.comparing(m -> (String) ((Map) m).get("name"))
                .thenComparing(Comparator.comparing(m -> (int) ((Map) m).get("age")).reversed()))
                .collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect));
    }

    public static void filterByKey() {
        List<String> list = new ArrayList<>();
//        先按name 字母顺序正序，然后按age 数字倒序
        List<String> collect = list.stream().filter(s -> !list.contains(s)).collect(Collectors.toList());

    }

    public static void filterMapByKey() {
        List<Object> keys = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(8);
        map.put("name", "ZK");
        map.put("age", 13);
        Map<String, Object> map2 = new HashMap<>(2);
        map2.put("name", "ZA");
        map2.put("age", 15);
        Map<String, Object> map3 = new HashMap<>(16);
        map3.put("name", "CX");
        map3.put("age", 20);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("name", "CX");
        map4.put("age", 18);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        List<Map<String, Object>> collect1 = list.stream().filter(m -> {
            boolean flag = !keys.contains(m.get("name"));
            if (flag) {
                keys.add(m.get("name"));
            }
            return flag;
        }).collect(Collectors.toList());
        List<Map<String, Object>> collect2 = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> (String) m.get("name")))), ArrayList::new));
        TreeSet<Map<String, Object>> maps = new TreeSet<>((o1, o2) -> {
            if (o1.get("name").equals(o2.get("name"))) {
                return 0;
            }
            return 1;
        });
        maps.addAll(list);
        List<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>(maps);
//        Set<UserCar> personSet = new TreeSet<UserCar>((o1, o2) ->
        List<Map> collect4 = list.stream().collect(Collectors.toMap(m -> m.get("name"), Function.identity(), (oldData,
                                                                                                              newData) -> newData)).values().stream().collect(Collectors.toList());
        List<Map> collect3 = new ArrayList<>(list.stream().collect(Collectors.toMap(m -> m.get("name"), Function
                .identity(), (oldData, newData) -> newData)).values());
//
//        List<Map<String, Object>> collect6 =list.stream().collect(
//                Collectors.toCollection(Collectors.toMap(m
//                        -> {
//                    return (((Map) m).get(""));
//
//                }, Function.identity(), (oldData, newData) -> newData))
//                , HashMap::new);
        System.out.println("arrayList>>>>:" + JSON.toJSONString(arrayList));
        System.out.println("collect4>>>>:" + JSON.toJSONString(collect4));
        System.out.println("collect3>>>>:" + JSON.toJSONString(collect3));
        System.out.println("collect1>>>>:" + JSON.toJSONString(collect1));
        System.out.println("collect2>>>>:" + JSON.toJSONString(collect2));
    }

    public static void main(String[] args) {
//        sort();
        filterMapByKey();
    }
}
