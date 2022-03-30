package com.laowengs.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "127.0.0.1:18080",name = "mock")
public interface TestService {

    @RequestMapping(value = "/mock/2ca458b66a174dcdb3b99148bbbcd40d/test",method = RequestMethod.POST)
    String index(@RequestBody TestRequest request);
}
