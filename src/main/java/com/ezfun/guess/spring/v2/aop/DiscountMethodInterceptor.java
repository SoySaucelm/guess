package com.ezfun.guess.spring.v2.aop;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author SoySauce
 * @date 2020/7/3
 */
@Slf4j
public class DiscountMethodInterceptor implements MethodInterceptor {

    private static final Integer DEFAULT_DISCOUNT_RATIO = 80;
//    private static final IntRange RATIO_RANGE = new IntRange(5,95);

    private Integer discountRatio = DEFAULT_DISCOUNT_RATIO;
    private boolean campaignAvailable;


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object returnValue = invocation.proceed();
        if (getDiscountRatio() > 0 && isCampaignAvailable()) {
            log.info("returnValue" + returnValue);
            return (Integer.valueOf((String) returnValue)) * getDiscountRatio() / 10;
        }
        return returnValue;
    }

    public Integer getDiscountRatio() {
        return discountRatio;
    }

    public void setDiscountRatio(Integer discountRatio) {
        this.discountRatio = discountRatio;
    }

    public boolean isCampaignAvailable() {
        return campaignAvailable;
    }

    public void setCampaignAvailable(boolean campaignAvailable) {
        this.campaignAvailable = campaignAvailable;
    }
}
