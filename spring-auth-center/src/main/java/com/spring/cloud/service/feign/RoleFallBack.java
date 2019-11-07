package com.spring.cloud.service.feign;

import org.springframework.security.Role;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RoleFallBack implements RoleInterface {

    @Override
    public List<Role> findRoles() {
        return null;
    }

    @Override
    public Role findRoleById(String roleId) {
        return null;
    }
}
