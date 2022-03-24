package com.laowengs.feign.controller;

import com.laowengs.feign.client.TestRequest;
import com.laowengs.feign.client.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestsController {

    private TestService testService;

    @Autowired
    public TestsController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping(value = "/tester",method = RequestMethod.POST)
    public TestRequest tester(@RequestBody TestRequest object){
        String index = testService.index(object);
        object.setName(object.getName()+index);
        return object;
    }
}
