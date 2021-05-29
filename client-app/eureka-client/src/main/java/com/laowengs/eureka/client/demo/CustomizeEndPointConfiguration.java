package com.laowengs.eureka.client.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomizeEndPointConfiguration {

    @Bean
    public DemoEndpoint demoEndpoint() {
        return new DemoEndpoint();
    }
}
