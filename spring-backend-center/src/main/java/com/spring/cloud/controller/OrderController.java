package com.spring.cloud.controller;

import com.spring.cloud.support.mvc.ResourceDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
@RefreshScope
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;



    @Value("${test}")
    private String parameter;

    @RequestMapping(value = "/home")
    @ResourceDesc(model = "订单管理", name = "订单添加", desc = "订单添加处理",version = 1)
    public String home() {
        logger.info("需要注意，我们通常在日志文件路径上使用使用一些系统变量（比如${catalina.base}）、或者");
        return "1";
    }

    @RequestMapping(value = "/home/{test}/{test1}")
    @ResourceDesc(model = "订单管理", name = "订单添加", desc = "订单添加处理")
    public String test(@PathVariable(value = "test") String test,@PathVariable(value = "test1") String test1) {
        return "1";
    }

    @RequestMapping(value = "/home")
    @ResourceDesc(model = "订单管理", name = "订单添加", desc = "订单添加处理")
    public String home1() {
        logger.info("需要注意，我们通常在日志文件路径上使用使用一些系统变量（比如${catalina.base}）、或者");
        return "";
//        SecurityUser securityUser = RequestUserUtils.currentUser();
//        System.out.println(securityUser.getId());
//        System.out.println(securityUser.getUsername());
//        System.out.println(parameter);
//        List<ServiceInstance> instances = discoveryClient.getInstances("spring-user");
//        System.out.println(instances.size());
//        for (ServiceInstance serviceInstance : instances) {
//            System.out.println("url:"+serviceInstance.getUri());
//        }
//        String services = "Services: " + discoveryClient.getServices();
//        System.out.println(services);
//        return parameter+restTemplate.getForEntity("http://spring-user/home", String.class).getBody();
//        return parameter;

    }
}
