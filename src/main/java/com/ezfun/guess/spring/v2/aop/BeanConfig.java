package com.ezfun.guess.spring.v2.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SoySauce
 * @date 2020/7/3
 */
@Configuration
public class BeanConfig {
//    @Bean
    public NameMatchMethodPointcut nameMatchMethodPointcut() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("get", "query");
        return pointcut;
    }

    @Bean
    public NameMatchMethodPointcutAdvisor advisor() {
        Advice advice = discountMethodInterceptor();//任何类型的Advice Introduction类型除外
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor(advice);
        advisor.setMappedNames("getGuess"); //在使用springaop对目标对象增强时，若切点的条件过于宽泛就会出现以下异常！
        // 以上问题出现的原因为aop定义的切点条件中，包含了被final所修饰的类。
        advisor.setOrder(Integer.MAX_VALUE);
//        advisor.setMappedNames(new String[]{"get","query"});
        return advisor;
    }

//    @Bean
    public DiscountMethodInterceptor discountMethodInterceptor() {
        DiscountMethodInterceptor interceptor = new DiscountMethodInterceptor();
        interceptor.setCampaignAvailable(true);
        interceptor.setDiscountRatio(2);
        return interceptor;
    }
}
