package com.ezfun.guess.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author SoySauce
 * @date 2020/3/25
 */
@Component
public class MyHealth implements HealthIndicator {
    @Override
    public Health health() {
        int code = check();
        if (code > 1) {
            return Health.up().withDetail("MyHealth is running", code).build();
        }
        return Health.down().withDetail("MyHealth stop running", code).build();
    }

    private int check() {
        return new Random().nextInt(3);
    }
}
