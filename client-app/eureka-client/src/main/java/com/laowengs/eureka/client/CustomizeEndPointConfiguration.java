package com.laowengs.eureka.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomizeEndPointConfiguration {

    @Bean
    public LoggersEndpoint2 loggersEndpoint2() {
        return new LoggersEndpoint2();
    }
}
