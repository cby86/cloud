package com.spring.cloud.service.feign;

import org.springframework.security.SecurityUser;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserFallBack implements UserInterface {
    @Override
    public SecurityUser findUserByName(String username) {
        return null;
    }
}