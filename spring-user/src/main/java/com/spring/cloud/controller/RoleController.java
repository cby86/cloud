package com.spring.cloud.controller;

import com.spring.cloud.base.BaseController;
import com.spring.cloud.controller.command.RoleCommand;
import com.spring.cloud.entity.Role;
import com.spring.cloud.service.RoleService;
import com.spring.cloud.support.AuthenticationChangeRemoteApplicationEvent;
import com.spring.cloud.support.RoleChangeEvent;
import com.spring.cloud.support.mvc.ResourceDesc;
import com.spring.cloud.utils.CommandUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
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
    @Autowired
    private BusProperties busProperties;

    @RequestMapping("/findRoles")
    @ResourceDesc(model = "角色管理", name = "根据条件查询角色分页列表", desc = "根据条件查询角色分页列表")
    public Map<String, Object> findRoles(String name, String code, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Role> roleList = roleService.findRoleList(name, code, pageable);
        return this.resultMap(CommandUtils.responsePage(roleList.getTotalElements(), roleList.getTotalPages(),
                CommandUtils.toCommands(roleList.getContent(), RoleCommand.class)));
    }

    @RequestMapping("/findRoleById")
    @ResourceDesc(model = "角色管理", name = "根据Id查询角色", desc = "根据Id查询角色")
    public Map<String, Object> findRoleById(String roleId) {
        Role role = roleService.findRoleById(roleId);
        return this.resultMap(new RoleCommand().fromDomain(role));
    }

    @RequestMapping("/updateRole")
    @ResourceDesc(model = "角色管理", name = "修改或者添加角色", desc = "修改或者添加角色")
    public Map<String, Object> updateRoles(RoleCommand roleCommand) {
        Role role = roleCommand.toDomain();
        roleService.saveOrUpdate(role);
        if (StringUtils.isNotEmpty(roleCommand.getId())) {
            publishEvent(new AuthenticationChangeRemoteApplicationEvent(role.getId(),busProperties.getId()));
        }
        publishEvent(new RoleChangeEvent(role.getId()));
        return this.resultMap(null);
    }

    @RequestMapping("/deleteRole")
    @ResourceDesc(model = "角色管理", name = "删除角色", desc = "删除角色")
    public Map<String, Object> deletedRole(String roleId) {
        roleService.deletedRole(roleId);
        return this.resultMap(true);
    }

    @RequestMapping("/findAllRoles")
    @ResourceDesc(model = "角色管理", name = "查询所有角色", desc = "查询所有角色")
    public Map<String, Object> findAllRoles() {
        List<Role> roleList = roleService.findAllRoles();
        return this.resultMap(CommandUtils.toCommands(roleList,RoleCommand.class));
    }


}

