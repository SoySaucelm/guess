package com.ezfun.guess.spring.v2.bean;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @author SoySauce
 * @date 2020/5/27
 */
@Data
@Component
@DependsOn({"FXNewsConfig"})
public class FXNewsProvider implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger("newsFactory");


    private String title;
    private String source;
    private String tag;
    private Integer a;
    private int b;

    public FXNewsProvider() {
    }


    private FXNewsConfig fxNewsConfig;


    @Autowired
    public FXNewsProvider(FXNewsConfig fxNewsConfig) throws InterruptedException {
//        this.title = fxNewsConfig.getTitle();
        LOGGER.info("init FXNewsProvider by ArgsConstructor fxNewsConfig");
        if (fxNewsConfig!=null){
            this.title = fxNewsConfig.getTitle();
//            this.fxNewsConfig = fxNewsConfig;
        }
    }


//    public FXNewsProvider(String title, String source) {
//        this.title = title;
//        this.source = source;
//    }
//
//    public FXNewsProvider(String title, String source, String tag) {
//        this.title = title;
//        this.source = source;
//        this.tag = tag;
//    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(this.title = fxNewsConfig.getTitle());
//        System.out.println(this.title = fxNewsConfig.getTitle());
    }
}
