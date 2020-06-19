package com.ezfun.guess.spring.v2.bean;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * @author SoySauce
 * @date 2020/6/17
 */
public class MyResourceLoader {

    public static void main(String[] args) throws IOException {
        Locale locale = new Locale("zh", "cn");
        Locale china = Locale.CHINA;

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(new FileSystemResourceLoader());
        Resource resource1 = resolver.getResource("b.txt");
        DefaultResourceLoader loader = new DefaultResourceLoader();
//        Resource resource = loader.getResource("E:\\work\\myCode\\guess\\b.txt");
        Resource resource = new FileSystemResourceLoader().getResource("b.txt");
        InputStream inputStream = resource.getInputStream();
        String result = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
        String result2 = FileCopyUtils.copyToString(new InputStreamReader(resource1.getInputStream()));
        System.out.println(result);
        System.out.println(result2);
//        AbstractApplicationContext


    }
}
