package com.laowengs.eureka.client;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

//@Component
public class DemoIndicator implements HealthIndicator {


    @Override
    public Health health() {
        long count = System.currentTimeMillis();
        Health health;
        if (count > 0) {
            health = Health.up()
                    .withDetail("count", count)
                    .withDetail("message", "We have enough coffee.")
                    .build();
        } else {
            health = Health.down()
                    .withDetail("count", 0)
                    .withDetail("message", "We are out of coffee.")
                    .build();
        }
        return health;
    }

}