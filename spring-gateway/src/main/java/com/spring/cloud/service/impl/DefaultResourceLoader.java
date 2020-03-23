package com.spring.cloud.service.impl;

import com.spring.cloud.config.ResourceLoader;
import com.spring.cloud.service.feign.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class DefaultResourceLoader implements ResourceLoader {

    @Autowired
    private UserInterface userInterface;
    @Override
    public Map<String, Set<String>> loadResource() {
        return  userInterface.loadAuthentications();
    }
}
