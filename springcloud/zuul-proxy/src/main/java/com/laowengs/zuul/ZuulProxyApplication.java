package com.laowengs.zuul;

import com.netflix.discovery.DiscoveryManager;
import com.netflix.loadbalancer.IRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@RestController
@RibbonClient(name = "*",configuration= ZuulProxyApplication.ConfigBean.class)
@ComponentScan("com.laowengs")
public class ZuulProxyApplication implements DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(ZuulProxyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ZuulProxyApplication.class, args);
    }

    @Autowired
    DiscoveryClient discoveryClient;


    @GetMapping("/serviceInfo")
    public Map<String,List<Map<String,Object>>> serviceInfo(){
        Map<String,List<Map<String,Object>>> map = new HashMap<>();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<Map<String,Object>> list = new ArrayList<>();
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                Map<String,Object> instanceMap = new HashMap<>();
                instanceMap.put("instanceId",instance.getInstanceId());
                instanceMap.put("uri",instance.getUri());
                instanceMap.put("metadata",instance.getMetadata());
                instanceMap.put("scheme",instance.getScheme());
                instanceMap.put("host",instance.getHost());
                instanceMap.put("port",instance.getPort());
                list.add(instanceMap);
            }
            map.put(service,list);
        }
        return map;
    }




    @Override
    public void destroy() throws Exception {
        DiscoveryManager.getInstance().shutdownComponent();
    }


    @Configuration
    public class ConfigBean {
        @Bean
        @LoadBalanced //Ribbon 是客户端负载均衡的工具；
        public RestTemplate getRestTemplate() {
            return new RestTemplate();
        }

        @Bean
        public IRule myRule() {
            return new GaryRibbonRule(); //自定义负载均衡规则
        }
    }
}
