package com.spring.cloud.service.feign;

import com.spring.cloud.security.Role;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RoleFallBack implements RoleInterface {
    @Override
    public Role findRoleById(String roleId) {
        return null;
    }
}
