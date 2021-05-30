package com.laowengs.eureka.client.demo.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomCommandLineRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CustomCommandLineRunner.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("CustomCommandLineRunner ....{}", (Object) args);
    }
}
