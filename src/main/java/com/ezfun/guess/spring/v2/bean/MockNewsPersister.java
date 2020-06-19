package com.ezfun.guess.spring.v2.bean;

import org.springframework.beans.factory.ObjectFactory;

/**
 * @author SoySauce
 * @date 2020/6/14
 */

public class MockNewsPersister {

    private ObjectFactory newsBeanFactory;

    public void persistNews() {
        System.out.println("persist bean:" + getNewsBean());
    }

    public FXNewsBean getNewsBean() {
        return (FXNewsBean) newsBeanFactory.getObject();
    }

    public void setNewsBeanFactory(ObjectFactory newsBeanFactory) {
        this.newsBeanFactory = newsBeanFactory;
    }
}
