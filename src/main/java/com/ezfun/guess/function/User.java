package com.ezfun.guess.function;

import lombok.Data;

/**
 * @author SoySauce
 * @date 2019/11/29
 */
@Data
public class User {
    private Long id;
    private String name;
    private String hobby;
    private Integer age;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(Long id, String name, String hobby, Integer age) {
        this.id = id;
        this.name = name;
        this.hobby = hobby;
        this.age = age;
    }

}
