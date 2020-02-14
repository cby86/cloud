package com.spring.cloud.service;

import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.Role;
import com.spring.cloud.repository.component.ResourcePermit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author panyuanjun
 * @Date 2019/11/8/008 11:03
 **/
public interface RoleService {

    void saveOrUpdate(Role role);

    Role findRoleById(String id);

    Page<Role> findRoleList(String name,String code, Pageable pageable);

    List<ResourcePermit> loadAuthentications();

    boolean hasSameName(String id, String name);

    void deletedRole(String roleId);

    List<Role> findAllRoles();
}
