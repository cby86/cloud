package com.spring.cloud.service;

import com.spring.cloud.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findRoles();
    Role findRoleById(String roleId);
}
