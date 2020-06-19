package com.ezfun.guess.spring.v2.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author SoySauce
 * @date 2020/6/15
 */
public class MyBeanPostProcessor implements BeanPostProcessor,InitializingBean {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof PasswordDecodeable) {
            String encodePassword = ((PasswordDecodeable) bean).getEncodePassword();
            String decodePassword = decodePassword(encodePassword);
            ((PasswordDecodeable) bean).setDecodePassword(decodePassword);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private String decodePassword(String encodePassword) {
        //demo....
        StringBuilder sb = new StringBuilder();
        for (byte b : (encodePassword).getBytes()) {
            sb.append(b ^ 1);
        }
        return sb.toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
