package com.ezfun.guess.demo;

/**
 * @author SoySauce
 * @date 2019/4/28
 */
@FunctionalInterface
public interface Html2Bean<T, R>{

    /**
     * T converter R
     *
     * @param t
     * @return R
     * @throws Exception
     */
    R converter(T t) throws Exception;

    /**
     * 默认方法不是抽象方法，其有一个默认实现，所以是符合函数式接口的定义的
     */
    default void speak() {
        System.out.println("say nihao");
    }

    /**
     * 静态方法不能是抽象方法，是一个已经实现了的方法，所以是符合函数式接口的定义的；
     *
     * @return
     */
    static void say() {
        System.out.println("say say");
    }

    /**
     * from Object
     *
     * @return
     */
    @Override
    String toString();


    /**
     * 函数式接口里是可以包含Object里的public方法，这些方法对于函数式接口来说，
     * 不被当成是抽象方法（虽然它们是抽象方法）；因为任何一个函数式接口的实现，
     * 默认都继承了Object类，包含了来自java.lang.Object里对这些抽象方法的实现；
     *
     * @param obj
     * @return
     */
    @Override
    boolean equals(Object obj);
}
