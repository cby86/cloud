package com.spring.cloud.controller;

import com.spring.cloud.base.BaseController;
import com.spring.cloud.controller.command.RoleCommand;
import com.spring.cloud.entity.Role;
import com.spring.cloud.service.RoleService;
import com.spring.cloud.utils.CommandUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, Object> findRoles(String name, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Role> roleList = roleService.findRoleList(name,pageable);
        return this.resultMap(CommandUtils.responsePage(roleList.getTotalElements(), roleList.getTotalPages(),
                CommandUtils.toCommands(roleList.getContent(), RoleCommand.class)));
    }

    @RequestMapping("/findRoleById")
    public Role findRoleById(String roleId) {
        return roleService.findRoleById(roleId);
    }

    @RequestMapping("/updateRole")
    public Map<String, Object> updateRoles(RoleCommand roleCommand) {
        Role role = roleCommand.toDomain();
        roleService.saveOrUpdate(role);
        return this.resultMap(null);
    }

}

