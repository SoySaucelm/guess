package com.ezfun.guess.spring.v2.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author SoySauce
 * @date 2020/6/14
 */
@Component("newsBean")
@Scope("prototype")
public class FXNewsBean {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
