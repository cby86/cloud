package com.spring.cloud.service.impl;

import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.Role;
import com.spring.cloud.repository.RoleRepository;
import com.spring.cloud.repository.component.ResourcePermit;
import com.spring.cloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author panyuanjun
 * @Date 2019/11/8/008 11:03
 **/
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void saveOrUpdate(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findRoleById(String id) {
        return roleRepository.findRoleById(id);
    }

    @Override
    public List<Role> findRoleList() {
        return roleRepository.findRoles();
    }

    @Override
    public List<ResourcePermit> loadAuthentications() {
        return roleRepository.loadAuthentications();
    }

}
