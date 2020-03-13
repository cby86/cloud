package com.spring.cloud.service.feign;

import com.spring.cloud.global.ResourceDefine;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ResourceFallBack implements ResourceInterface{
    @Override
    public void registerEndpoint(List<ResourceDefine> endpoints) {

    }
}
