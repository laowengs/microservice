package com.laowengs.eureka.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("demo")
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("thymeleaf")
    public String thymeleaf(){
        return "thymeleaf";
    }

    @RequestMapping("demo")
    @ResponseBody
    @HystrixCommand(fallbackMethod = "fallback")
    public String demo(){
        logger.info("call demo/demo");
        return "demo";
    }

    public String fallback(){
        return "fallback";
    }

}
