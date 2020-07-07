package com.ezfun.guess.spring.v2;

import com.ezfun.guess.spring.v2.bean.BeanUtils;
import com.ezfun.guess.spring.v2.bean.FXNewsConfig;
import com.ezfun.guess.spring.v2.bean.FXNewsProvider;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.beans.factory.support.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.util.Properties;

/**
 * @author SoySauce
 * @date 2020/5/27
 */
public class MyBeanFactory {


    public static Object readYml2Bean(Class<?> clz) throws Exception {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        Resource resource = new ClassPathResource("config.yml");
        yamlPropertiesFactoryBean.setResources(resource);
        Properties prop = yamlPropertiesFactoryBean.getObject();
        Object obj = clz.newInstance();
        BeanUtils.copyProperties(prop, obj);
    /*    YamlPropertySourceLoader ypsl = new YamlPropertySourceLoader();
        List<PropertySource<?>> load = ypsl.load("config.yml", resource);
        for (PropertySource<?> propertySource : load) {
            Object property = propertySource.getProperty("title");
        }
        return load;*/
        return obj;
    }


    private static BeanFactory bindViaFile(BeanDefinitionRegistry beanRegistry) {
        AbstractBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(beanRegistry) {

            @Override
            public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
                return this.loadBeanDefinitions(new EncodedResource(resource, "utf-8"), null);
            }
        };
        reader.loadBeanDefinitions("classpath:config.txt");//配置文件路径


        // xml
//        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(beanRegistry);
//        xmlReader.loadBeanDefinitions("classpath:config.xml");
        //或者直接
//        new XmlBeanFactory(new ClassPathResource("config.xml"));

        return (BeanFactory) beanRegistry;

    }


    public static void main(String[] args) throws Exception {
//        BeanFactory container = new XmlBeanFactory(new ClassPathResource("spring.xml"));
        //或者
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        //亦或者
//        ApplicationContext container = new FileSystemXmlApplicationContext("spring.xml");
        FXNewsConfig fxNewsConfig = (FXNewsConfig) context.getBean(FXNewsConfig.class);
        FXNewsProvider fxNewsProvider = (FXNewsProvider) context.getBean(FXNewsProvider.class);



        readYml2Bean(FXNewsProvider.class);
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
//        BeanFactory container = bindViaCode(beanRegistry);
        BeanFactory container = bindViaFile(beanRegistry);
        FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("newsProvider");
        System.out.println(newsProvider);
    }


    /**
     * bind Via Code 通过代码绑定
     *
     * @param beanRegistry BeanDefinitionRegistry
     * @return BeanFactory
     */
    private static BeanFactory bindViaCode(BeanDefinitionRegistry beanRegistry) {
        AbstractBeanDefinition newsProvider = new RootBeanDefinition(FXNewsProvider.class);
        beanRegistry.registerBeanDefinition("newsProvider", newsProvider);
        //通过构造方法注入
        ConstructorArgumentValues argumentValues = new ConstructorArgumentValues();
        argumentValues.addIndexedArgumentValue(0, TITLE);
        argumentValues.addIndexedArgumentValue(1, SOURCE);
        argumentValues.addIndexedArgumentValue(2, TAG);
        newsProvider.setConstructorArgumentValues(argumentValues);
        //通过setter方法注入
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("title", TITLE));
        propertyValues.addPropertyValue("source", SOURCE);
        newsProvider.setPropertyValues(propertyValues);
        return (BeanFactory) beanRegistry;
    }



















    private static final String TITLE = "震惊!!!某富二代...";
    private static final String SOURCE = "UC";
    private static final String TAG = "娱乐";


}
