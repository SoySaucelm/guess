package com.ezfun.guess.spring.v2.bean;

import org.joda.time.DateTime;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author SoySauce
 * @date 2020/6/14
 */
public class NextDayDateFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new DateTime().plusDays(1);
    }

    @Override
    public Class<?> getObjectType() {
        return DateTime.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
