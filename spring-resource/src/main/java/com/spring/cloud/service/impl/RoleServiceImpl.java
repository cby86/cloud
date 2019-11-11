package com.spring.cloud.service.impl;

import com.spring.cloud.entity.Menu;
import com.spring.cloud.entity.Role;
import com.spring.cloud.repository.RoleRepository;
import com.spring.cloud.service.MenuService;
import com.spring.cloud.service.RoleService;
import org.apache.commons.lang.StringUtils;
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

    @Autowired
    MenuService menuService;

    @Override
    public void saveOrUpdate(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deletedRole(String id) {
        roleRepository.deleteById(id);
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
    public void saveOrUpdate(String id, String name, String code, String menuIds) {
        Role role = null;
        if (StringUtils.isEmpty(id)) {
            role = new Role();
        } else {
            role = findRoleById(id);
        }
        if (role == null) {
            throw new UnsupportedOperationException("非法数据请求");
        }
        role.setCode(code);
        role.setName(name);
        if (StringUtils.isNotEmpty(menuIds)) {
            List<Menu> menus = menuService.findMenuByIds(menuIds);
            role.setMenuList(menus);
        }
        this.saveOrUpdate(role);
    }
}
