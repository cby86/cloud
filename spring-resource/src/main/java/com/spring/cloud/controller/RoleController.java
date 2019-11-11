package com.spring.cloud.controller;

import com.alibaba.fastjson.JSON;
import com.spring.cloud.base.BaseController;
import com.spring.cloud.entity.Role;
import com.spring.cloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色接口
 *
 * @author
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    RoleService roleService;

    @RequestMapping("/findRoles")
    public Map<String, Object> findRoles() {
        List<Role> roleList = roleService.findRoleList();
        return this.resultMap("0", "success", roleList);
    }

    @RequestMapping("/findRoleById")
    public Role findRoleById(String roleId) {
        return roleService.findRoleById(roleId);
    }


    @RequestMapping("/saveRole")
    public Map<String, Object> saveRole(String id, String code, String menuIds) {
        roleService.saveOrUpdate(id, code, menuIds);
        return this.resultMap("0", "success", null);
    }
}

