package com.spring.cloud.service;

import com.spring.cloud.entity.Role;

import java.util.List;

/**
 * @Author panyuanjun
 * @Date 2019/11/8/008 11:03
 **/
public interface RoleService {

    void saveOrUpdate(Role role);

    void deletedRole(String id);

    Role findRoleById(String id);

    List<Role> findRoleList();

    void saveOrUpdate(String id, String code, String menuIds);
}
