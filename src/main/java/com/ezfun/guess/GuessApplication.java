package com.ezfun.guess;

import com.ezfun.guess.spring.v2.bean.MockNewsPersister;
import com.ezfun.guess.spring.v2.event.MethodExecutionEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.ezfun.guess.spring.*")})
@RestController
@Slf4j
public class GuessApplication {
    public static void main(String[] args) {
        SpringApplication.run(GuessApplication.class, args);
    }

    public static void main2(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(GuessApplication.class, args);
        //        AnnotationConfigApplicationContext applicationContext2 = new AnnotationConfigApplicationContext();
//        String[] definitionNames = applicationContext2.getBeanDefinitionNames();
//        for (String name : definitionNames) {
//            System.out.println(name);
//        }
//        ApplicationContext ctx = SpringApplication.run(CouponApiApplication.class, args);
        //所有的bean,参考：http://412887952-qq-com.iteye.com/blog/2314051
        String[] beanNames = run.getBeanDefinitionNames();
        //String[] beanNames = ctx.getBeanNamesForAnnotation(RestController.class);//所有添加该注解的bean
        log.info("bean总数:{}", run.getBeanDefinitionCount());
        Object nextDayDate = run.getBean("&nextDayDate");
        Object nextDayDate2 = run.getBean("&nextDayDate");
        System.out.println("nextDayDate" + nextDayDate);
        System.out.println("nextDayDate2" + nextDayDate2);

        MockNewsPersister mockNewsPersister = (MockNewsPersister) run.getBean("mockNewsPersister");
        mockNewsPersister.persistNews();
        mockNewsPersister.persistNews();
        mockNewsPersister.persistNews();
        ((MethodExecutionEventPublisher) run.getBean("methodExecutionEventPublisher")).methodToMonitor();


//        int i = 0;
//        for (String str : beanNames) {
//            log.error("{},beanName:{}", ++i, str);
//        }
    }

    @RequestMapping("/user")
    public Map test(@RequestBody Map map) {
        return map;
    }

    @RequestMapping("/get")
    public Object getGuess() {
        System.out.println("aop");
        return "1000";
    }


//    @Bean
//    public NameMatchMethodPointcut myMethodMatcher() {
//        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
//        pointcut.addMethodName("test");
//        return pointcut;
//    }

   /* @PostConstruct
    public void run(){
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        String path = systemClassLoader.getResource("").getPath().replace("/classes", "");
        System.out.println(path);
        try {
            ClassPathResource classPathResource = new ClassPathResource("/pom.properties");
            Properties config = new Properties();
            config.load(classPathResource.getInputStream());
            System.out.println("pom 版本3:"+config.getProperty("version"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileSystemResource fileSystemResource = new FileSystemResource(path + "maven-archiver" + "/pom.properties");
            Properties config = new Properties();
            config.load(fileSystemResource.getInputStream());
            System.out.println("pom 版本1:"+config.getProperty("version"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileSystemResource fileSystemResource = new FileSystemResource(path + "META-INF/maven/com.ezfun.guess/guess/pom.properties");
            Properties config = new Properties();
            config.load(fileSystemResource.getInputStream());
            System.out.println("pom 版本2:"+config.getProperty("version"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}

