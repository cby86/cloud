package com.spring.cloud.service;

import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.Role;
import com.spring.cloud.repository.component.ResourcePermit;

import java.util.List;

/**
 * @Author panyuanjun
 * @Date 2019/11/8/008 11:03
 **/
public interface RoleService {

    void saveOrUpdate(Role role);

    Role findRoleById(String id);

    List<Role> findRoleList();

    List<ResourcePermit> loadAuthentications();
}
