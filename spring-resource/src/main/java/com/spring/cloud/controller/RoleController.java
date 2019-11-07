package com.spring.cloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RoleController {
    @RequestMapping("/findRoles")
    public String findRoles() {
        return "";
    }

    @RequestMapping("/findRoleById")
    public String findRoleById() {
        return "";
    }

}

