package com.spring.cloud.service.impl;
import com.spring.cloud.config.ResourceLoader;
import com.spring.cloud.service.feign.UserInterface;
import com.spring.cloud.service.feign.component.ResourcePermit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultResourceLoader implements ResourceLoader {

    @Autowired
    private UserInterface userInterface;
    @Override
    public Map<String, List<String>> loadResource() {
        Map<String, List<String>> resource = new HashMap<>();
        List<ResourcePermit> list = userInterface.loadAuthentications();
        if (list == null) {
            return resource;
        }
        for (ResourcePermit resourcePermit : list) {
            resource.put(resourcePermit.getUrl(),  Arrays.asList(resourcePermit.getRoles().split(",")));
        }
        return resource;
    }
}
