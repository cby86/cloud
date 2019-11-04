package com.spring.cloud.service.impl;
import com.spring.cloud.entity.Role;
import com.spring.cloud.repository.RoleRepository;
import com.spring.cloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Override
    public List<Role> findRoles() {
        return roleRepository.findRoles();
    }

    @Override
    public Role findRoleById(String roleId) {
        return roleRepository.findRoleById(roleId);
    }
}
