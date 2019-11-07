package com.spring.cloud.service.feign;
import org.springframework.security.SecurityUser;
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

    @Override
    public SecurityUser findUserByName(String username) {
        return null;
    }
}
