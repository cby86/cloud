package com.spring.cloud.controller;

import com.spring.cloud.service.feign.ResourceService;
import com.spring.cloud.utils.RequestUserUtils;
import com.spring.cloud.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.SecurityUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/6/006 17:22
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ResourceService resourceService;


    @RequestMapping("/findRoles")
    public Map<String, Object> findRoles() {
        Map<String, Object> roles = resourceService.findRoles();
        return roles;
    }

    @RequestMapping("/findMenus")
    public Map<String, Object> findMenus() {
        return resourceService.findMenus();
    }


}
