package com.laowengs.eureka.client;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;

import java.util.HashMap;
import java.util.Map;


//@Component
public class DemoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Map<String,Object> info = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            info.put(String.valueOf(i), Math.random());
        }
        builder.withDetail("version", info);
    }
}
