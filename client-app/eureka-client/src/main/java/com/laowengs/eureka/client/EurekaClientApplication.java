package com.laowengs.eureka.client;

import com.laowengs.security.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@EnableEurekaClient
@SpringBootApplication
@EnableCircuitBreaker
@Import({WebSecurityConfig.class})
public class EurekaClientApplication{

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }

}
