package com.ezfun.guess.spring.v2.config;

import com.ezfun.guess.spring.v2.bean.MockNewsPersister;
import com.ezfun.guess.spring.v2.bean.NextDayDateFactoryBean;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SoySauce
 * @date 2020/6/14
 */
@Configuration
public class BaseConfiguration {


    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        Scope myScope = new MyScope();
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("myScope", myScope);
        return configurer;
    }

    @Bean
    @org.springframework.context.annotation.Scope("prototype")
    public NextDayDateFactoryBean nextDayDate() {
        return new NextDayDateFactoryBean();
    }

    @Bean
    public ObjectFactoryCreatingFactoryBean newsBeanFactory() throws Exception {
        ObjectFactoryCreatingFactoryBean creatingFactoryBean = new ObjectFactoryCreatingFactoryBean();
        creatingFactoryBean.setTargetBeanName("newsBean");
        return creatingFactoryBean;
    }
    @Bean
    public MockNewsPersister mockNewsPersister(ObjectFactoryCreatingFactoryBean newsBeanFactory) throws Exception {
        MockNewsPersister mockNewsPersister = new MockNewsPersister();
        mockNewsPersister.setNewsBeanFactory(newsBeanFactory.getObject());
        return mockNewsPersister;
    }


}
