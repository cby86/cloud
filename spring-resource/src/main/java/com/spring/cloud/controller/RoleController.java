package com.spring.cloud.controller;

import com.spring.cloud.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Role;
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
        com.spring.cloud.entity.Role roleEntity = roleRepository.findRoleById(roleId);
        org.springframework.security.Role role = new Role();
        role.setName(roleEntity.getName());
        role.setCode(roleEntity.getCode());
        return role;
    }

}

