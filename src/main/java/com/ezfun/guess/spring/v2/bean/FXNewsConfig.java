package com.ezfun.guess.spring.v2.bean;

import com.ezfun.guess.spring.v2.MyBeanFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author SoySauce
 * @date 2020/5/28
 */
@Component
@Data
@Slf4j
public class FXNewsConfig {
    private String title;
    private String source;
    private String tag;

    public FXNewsConfig() {
        try {
            FXNewsProvider fx = (FXNewsProvider) MyBeanFactory.readYml2Bean(new FXNewsProvider(null).getClass());
            this.title = fx.getTitle();
            this.source = fx.getSource();
            this.tag = fx.getTag();
            log.info("init FXNewsConfig NoArgsConstructor...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
