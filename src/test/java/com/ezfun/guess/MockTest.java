package com.ezfun.guess;

import com.ezfun.guess.web.AppController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author SoySauce
 * @date 2020/3/25
 * @link https://stackoverflow.com/questions/21271468/spring-propertysource-using-yaml/51392715
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
//@SpringBootConfiguration
@ContextConfiguration(classes = AppController.class,initializers = ConfigFileApplicationContextInitializer.class)
//@PropertySource(value = {"classpath:conf/generator1.properties"})
//@Import(xx.class)
//@TestPropertySource(value = {"classpath:conf/generator1.properties"})
//@TestPropertySource("classpath:conf/generator1.properties")
@TestPropertySource("classpath:application.yml")

public class MockTest {

    private MockMvc mock;

    @Value("${param}")
    private String mapperPlugin;
    @Before
    public void setUp() throws Exception {
        System.out.println(mapperPlugin);
        mock = MockMvcBuilders.standaloneSetup(AppController.class).build();
    }

    @Autowired
    private AppController appController;
    @Test
    public void mockTest() throws Exception {
        System.out.println(mapperPlugin);
        String contentAsString = mock.perform(MockMvcRequestBuilders.get("/app")).andReturn().getResponse()
                .getContentAsString();
        System.out.println(contentAsString);
        appController.demo();
        System.out.println("\r \n \t est *******");
    }
}
