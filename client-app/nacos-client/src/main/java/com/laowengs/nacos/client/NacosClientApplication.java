package com.laowengs.nacos.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosClientApplication.class, args);
    }


    @Controller
    @RequestMapping("log")
    public static class LogController {

        @Value("${server.port}")
        private String port;

        @GetMapping("/level")
        @ResponseBody
        public String demo(){
            System.out.println("_");
            return port;
        }

    }
}
