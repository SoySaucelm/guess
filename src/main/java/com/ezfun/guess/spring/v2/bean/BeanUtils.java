package com.ezfun.guess.spring.v2.bean;

import org.springframework.beans.BeansException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Properties;

/**
 * @author SoySauce
 * @date 2020/5/28
 */
public class BeanUtils {

    public static void copyProperties(Object source, Object target) throws BeansException {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }


    public static void copyProperties(Properties source, Object target) throws IllegalAccessException {
        Class<?> clz = target.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(target, source.getProperty(field.getName(), null));
//            field.set(target, source.get(field.getName())) ;
        }

    }

    public static void main(String[] args) {

        Class<FXNewsProvider> clz = FXNewsProvider.class;
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field f : declaredFields) {
            Class<?> type = f.getType();
            Type genericType = f.getGenericType();
            boolean primitive = type.isPrimitive();
            System.out.println("typeName:" + type.getTypeName() + " isPrimitive>>>>>>>>>>>" + primitive + " " +
                    "genericType>>>>>>>>>" +
                    genericType
                            .getTypeName());
        }


    }
}
