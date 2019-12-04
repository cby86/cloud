package com.spring.cloud.controller;
import com.spring.cloud.repository.component.ResourcePermit;
import com.spring.cloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    RoleService roleService;
    @RequestMapping("/loadAuthentications")
    public List<ResourcePermit> loadAuthentications() {
        return roleService.loadAuthentications();
    }
}
