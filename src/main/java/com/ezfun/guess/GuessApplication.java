package com.ezfun.guess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class GuessApplication {

    public static void main(String[] args) {
             SpringApplication.run(GuessApplication.class, args);
    }
    @RequestMapping("/user")
    public Map test(@RequestBody Map map){

        return map;
    }

}

