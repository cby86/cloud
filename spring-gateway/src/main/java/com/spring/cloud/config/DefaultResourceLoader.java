package com.spring.cloud.config;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultResourceLoader implements ResourceLoader {
    @Override
    public Map<String, List<String>> loadResource() {
        Map resource = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add("ROLE1_ADMIN");
        roles.add("ROLE_ADMIN");
        resource.put("/backend/tt/**", roles);
        return resource;
    }
}
