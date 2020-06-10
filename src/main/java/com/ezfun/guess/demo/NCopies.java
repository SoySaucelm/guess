package com.ezfun.guess.demo;

import java.util.List;

/**
 * @author SoySauce
 * @date 2019/4/29
 */
public interface NCopies {
    public <T extends Cloneable> List<T> getCopies(T seed, int num);
}
