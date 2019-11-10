package com.spring.cloud.service.feign;

import com.spring.cloud.security.SecurityUser;
import org.springframework.stereotype.Component;

@Component
public class UserFallBack implements UserInterface {
    @Override
    public SecurityUser findUserByName(String username) {
        return null;
    }
}
