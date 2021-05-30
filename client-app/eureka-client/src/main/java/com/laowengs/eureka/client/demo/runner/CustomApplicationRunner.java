package com.laowengs.eureka.client.demo.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomApplicationRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(CustomApplicationRunner.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("CustomApplicationRunner ....{}", args);

    }
}
