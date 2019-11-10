package com.spring.cloud.controller;

import com.spring.cloud.utils.RequestUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import com.spring.cloud.security.SecurityUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
@RefreshScope
public class OrderController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;



    @Value("${test}")
    private String parameter;

    @RequestMapping("/home")
    public String home() {
        SecurityUser securityUser = RequestUserUtils.currentUser();
        System.out.println(securityUser.getId());
        System.out.println(securityUser.getUsername());
        System.out.println(parameter);
        List<ServiceInstance> instances = discoveryClient.getInstances("spring-user");
        System.out.println(instances.size());
        for (ServiceInstance serviceInstance : instances) {
            System.out.println("url:"+serviceInstance.getUri());
        }
        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
//        return restTemplate.getForEntity("http://spring-user/home", String.class).getBody();
        return parameter;

    }
}
