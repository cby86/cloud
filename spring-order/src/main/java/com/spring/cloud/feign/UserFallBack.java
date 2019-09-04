package com.spring.cloud.feign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Component;

@Component
public class UserFallBack implements UserInterface {
    @Override
    public String home() {
        return "goods";
    }

    @Override
    public String index() {
        System.out.println("sd");
        return "index";
    }
}
