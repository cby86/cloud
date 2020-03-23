package com.spring.cloud.controller;

import com.spring.cloud.repository.component.ResourcePermit;
import com.spring.cloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    RoleService roleService;

    @RequestMapping("/loadAuthentications")
    public Map<String, Set<String>> loadAuthentications() {
        Map<String, Set<String>> resource = new HashMap<>();
        List<ResourcePermit> resourcePermits = roleService.loadAuthentications();
        resourcePermits.forEach(resourcePermit -> {
            HashSet<String> roles = new HashSet<String>();
            if (resourcePermit.getPermit() > 0) {
                roles.addAll(Arrays.asList(resourcePermit.getRole().split(",")));
            }
            resource.put(resourcePermit.getUrl(), roles);
        });
        return resource;
    }
}
