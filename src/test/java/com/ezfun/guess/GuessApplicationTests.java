package com.ezfun.guess;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(value = {"classpath:conf/generator1.properties"})
public class GuessApplicationTests {

    @Value("${mapper.plugin}")
    private String mapperPlugin;
    @Test
    public void contextLoads() {
        System.out.println(mapperPlugin);
        System.out.println(mapperPlugin);
    }

}

