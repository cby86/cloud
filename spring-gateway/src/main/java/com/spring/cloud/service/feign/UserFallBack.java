package com.spring.cloud.service.feign;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class UserFallBack implements UserInterface {
    @Override
    public Map<String, Set<String>> loadAuthentications() {
        return new HashMap<>();
    }
}
