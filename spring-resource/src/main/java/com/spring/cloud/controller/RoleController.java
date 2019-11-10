package com.spring.cloud.controller;

import com.spring.cloud.entity.Role;
import com.spring.cloud.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;
    @RequestMapping("/findRoles")
    public String findRoles() {
        return "";
    }

    @RequestMapping("/findRoleById")
    public Role findRoleById(String roleId) {
        Role role = roleRepository.findRoleById(roleId);
        return role;
    }

}

