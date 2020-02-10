package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.Role;
import com.spring.cloud.exception.BusinessException;
import com.spring.cloud.service.RoleService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class RoleCommand implements Command<Role> {
    private String id;
    private String name;
    private String code;

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
        return role;
    }

    @Override
    public Command<Role> fromDomain(Role domain) {
        this.id = domain.getId();
        this.name = domain.getName();
        this.code = domain.getCode();
        return this;
    }
}
