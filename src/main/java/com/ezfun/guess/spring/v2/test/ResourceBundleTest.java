package com.ezfun.guess.spring.v2.test;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.StaticMessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author SoySauce
 * @date 2020/6/18
 */
public class ResourceBundleTest {
    public static void main(String[] args){
        ResourceBundle messages = ResourceBundle.getBundle("i18n/messages", Locale.CHINA);
        String content = messages.getString("menu.name");
        String content2 = messages.getString("menu.file");
        System.out.println(content);
        System.out.println(content2);

        StaticMessageSource staticMessageSource = new StaticMessageSource();
        MessageSource parent=new ResourceBundleMessageSource();
        ((ResourceBundleMessageSource) parent).setBasename("i18n/messages");
        staticMessageSource.setParentMessageSource(parent);
        String message = staticMessageSource.getMessage("menu.name", null, Locale.CHINA);
        String message2 = parent.getMessage("menu.name", null, Locale.CHINA);
        System.out.println(message);
        System.out.println(message2);
    }
}
