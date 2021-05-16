package com.laowengs.eureka.client;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class DemoControllerTest {

    @Test
    void name() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders(); // 定义一个HTTP的头信息
        String auth = "admin:123"; // 认证的原始信息
        byte[] encodedAuth = Base64.getEncoder()
                .encode(auth.getBytes(Charset.forName("US-ASCII"))); // 进行一个加密的处理
        // 在进行授权的头信息内容配置的时候加密的信息一定要与“Basic”之间有一个空格
        String authHeader = "Basic " + new String(encodedAuth);
        System.out.println(authHeader);
        System.out.println(new String(Base64.getDecoder().decode(authHeader.substring(6))));

        headers.set("Authorization", authHeader);
        headers.set("mir-version", "1.8");
        ResponseEntity<String> exchange = restTemplate.exchange("http://admin:123@localhost:18002/access/eureka-client/demo/demo", HttpMethod.GET,
                new HttpEntity<Object>(headers), String.class);
        System.out.println(exchange.getBody());
    }
}