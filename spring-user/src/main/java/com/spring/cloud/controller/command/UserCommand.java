package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.Role;
import com.spring.cloud.entity.User;
import com.spring.cloud.exception.BusinessException;
import com.spring.cloud.service.RoleService;
import com.spring.cloud.service.UserService;
import com.spring.cloud.utils.CommandUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserCommand implements Command<User> {
    private String id;
    private String username;
    private String password;
    private String roles;
    private List<String> roleIds;
    private boolean inner;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Override
    public User toDomain() {
        User user = null;
        if (StringUtils.isNotEmpty(id)) {
            user = userService.findUserById(id);
        } else {
            user = new User();
        }
        if (userService.hasSameName(id, username)) {
            throw new BusinessException("角色名称不能重复");
        }
        user.setUsername(this.username);
        if (StringUtils.isNotEmpty(password)) {
            user.setPassword(this.password);
        }

        if (!CollectionUtils.isEmpty(roleIds)) {
            for (String roleId : roleIds) {
                Role role = roleService.findRoleById(roleId);
                user.addRoles(role);
            }
        }
        return user;
    }

    @Override
    public Command<User> fromDomain(User domain) {
        this.id = domain.getId();
        this.username = domain.getUsername();
        this.inner = domain.isSystem();
        if (!CollectionUtils.isEmpty(domain.getRoles())) {
            roleIds = new ArrayList<>();
            List<String> roleList = new ArrayList<>();
            for (Role role : domain.getRoles()){
                roleList.add(role.getName());
                roleIds.add(role.getId());
            }
            this.roles = String.join(",", roleList);
        }
        return this;
    }
}
