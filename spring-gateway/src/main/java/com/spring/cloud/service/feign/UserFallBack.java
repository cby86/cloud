package com.spring.cloud.service.feign;


import com.spring.cloud.service.feign.component.ResourcePermit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFallBack implements UserInterface {
    @Override
    public List<ResourcePermit> loadAuthentications() {
        return null;
    }
}
