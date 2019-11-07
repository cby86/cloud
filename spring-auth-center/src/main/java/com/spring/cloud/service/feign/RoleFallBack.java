package com.spring.cloud.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@FeignClient(value = "spring-user",fallback = UserFallBack.class)
public class RoleFallBack implements RoleService {

    @Override
    public List<Role> findRoles() {
        return null;
    }

    @Override
    public Role findRoleById(String roleId) {
        return null;
    }
}
