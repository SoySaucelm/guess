package com.ezfun.guess.web;

import com.ezfun.guess.demo.Demo2;
import com.ezfun.guess.demo.Html2Json;
import com.ezfun.guess.demo.MyComparable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


/**
 * Created by liming on 2019/1/17.
 */
@RestController
@PropertySource("classpath:conf/generator1.yml")
public class AppController implements CommandLineRunner {

    //    public  <T extends Comparable<T>> void sort2(List<T> list, MyComparable<Integer> comp) {
//
//        // sort the list
//    }
    public static <T extends Comparable<T>>
    void sort(List<T> list, MyComparable<T> comp) {
    }

    @Value("${turl}")
    private String url;
    @Value("${tname}")
    private String name;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(new FileSystemResource("generator1.yml"));//File引入
        yaml.setResources(new ClassPathResource("conf/generator1.yml"));//class引入
        configurer.setProperties(yaml.getObject());
        return configurer;
    }

    @Bean
    public org.springframework.core.env.PropertySource properties(ConfigurableListableBeanFactory beanFactory) {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("properties.yml"));
        configurer.setProperties(yaml.getObject());
        // properties need to be processed by beanfactory to be accessible after
        configurer.postProcessBeanFactory(beanFactory);
        return configurer.getAppliedPropertySources().get(PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME);
    }


    static String getParam() {

        List<String> list = Arrays.asList("a", "b", "c");
        MyComparable<String> myComparable = (a, b) -> 9999;
        int compare = myComparable.compare("2", "2");
        System.out.println(compare);


        sort(list, (a, b) -> {
            a.contains(b);
            return 1;
        });

        return "1";
    }

    public static void main(String[] args) {

        getParam();
//        Html2Bean<String,Integer> html2Bean= getParam -> {return 1;};

        Html2Json html2Json = () -> {
            System.out.println("zzz");
            System.out.println("abc");
        };
        html2Json.test();

    }

    @RequestMapping("demo")
    public void demo() throws Exception {
        Demo2 demo2 = new Demo2();
        demo2.execute();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        demo2.printContent();
//        System.out.println(Demo2.sb);
        System.out.println("end");
    }

    @RequestMapping("app")
    public String testApp(HttpServletRequest request) {
//        req.ge
        if (request.getHeader("x-forwarded-for") == null) {
            System.out.println(request.getRemoteAddr());
        } else {
            System.out.println(request.getHeader("x-forwarded-for"));
        }
        System.out.println(getIpAddress(request));
        return "app";
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println(url);
        System.out.println(url + name);
    }

    @RequestMapping("shutdown")
    public void shutdown(HttpServletRequest request) {
        System.exit(0);
    }
}
