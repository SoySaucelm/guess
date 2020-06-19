package com.ezfun.guess.spring.v2.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;

/**
 * <p>
 * 容器实例化相应对象之前,对注册到容器的BeanDefinition所保存的信息做对应的修改 比如修改bean定义
 * 的某些属性,为bean定义增加其他信息等 因为一个容器可能拥有多个BeanFactoryPostProcessor 这个时候
 * 可能需要实现类同时实现Ordered接口以保证各个BeanFactoryPostProcessor可以按照预先设定的顺序执行(如果顺序紧要的话)
 * spring还允许我们通过CustomEditorConfigurer(字符串到具体对象的转换相关规则) 来注册自定义的PropertyEditor
 * PropertyResourceConfigurer convertProperties 如对加密后的字符串解密在覆盖到对应的bean定义中
 * </p>
 * <p>
 * spring提供的两个比较常用的实现类
 *
 * @author SoySauce
 * @date 2020/6/15
 * @see PropertyPlaceholderConfigurer
 * @see PropertyOverrideConfigurer
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        AbstractApplicationContext
//        CglibSubclassingInstantiationStrategy
    }
}
