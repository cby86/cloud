package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.Role;
import com.spring.cloud.exception.BusinessException;
import com.spring.cloud.service.RoleService;
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
public class RoleCommand implements Command<Role> {
    private String id;
    private String name;
    private String code;
    private boolean inner;
    private List<AuthenticationCommand> authentications;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Autowired
    RoleService roleService;

    @Override
    public Role toDomain() {
        Role role;
        if (StringUtils.isNotEmpty(id)) {
            role = roleService.findRoleById(id);
        } else {
            role = new Role();
        }
        if (roleService.hasSameName(id, code)) {
            throw new BusinessException("角色名称不能重复");
        }
        role.setName(this.name);
        role.setCode(this.code);
        if (!CollectionUtils.isEmpty(role.getAuthentications())) {
            role.getAuthentications().clear();
        }
        if (!CollectionUtils.isEmpty(authentications)) {
            for (AuthenticationCommand authenticationCommand:authentications) {
                role.addAuthentication(authenticationCommand.toDomain());
            }
        }
        return role;
    }

    @Override
    public Command<Role> fromDomain(Role domain) {
        this.id = domain.getId();
        this.name = domain.getName();
        this.code = domain.getCode();
        this.inner = domain.isSystem();
        if (!CollectionUtils.isEmpty(domain.getAuthentications())) {
            authentications = CommandUtils.toCommands(domain.getAuthentications(), AuthenticationCommand.class);
        }
        return this;
    }
}
