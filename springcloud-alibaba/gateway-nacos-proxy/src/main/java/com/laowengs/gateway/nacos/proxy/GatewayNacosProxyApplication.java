package com.laowengs.gateway.nacos.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class GatewayNacosProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayNacosProxyApplication.class, args);
    }
}
