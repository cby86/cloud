package com.spring.cloud;
import com.spring.cloud.service.feign.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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

    @Autowired
    private UserInterface userInterface;

    @Value("${test:9}")
    private String parameter;

    @RequestMapping("/home")
    public String home() {
        System.out.println(parameter);
        List<ServiceInstance> instances = discoveryClient.getInstances("spring-user");
        System.out.println(instances.size());
        for (ServiceInstance serviceInstance : instances) {
            System.out.println("url:"+serviceInstance.getUri());
        }
        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
//        return restTemplate.getForEntity("http://spring-user/home", String.class).getBody();
        return userInterface.index();

    }
}
