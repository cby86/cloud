package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.Authentication;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

@Getter
@Setter
public class AuthenticationCommand implements Command<Authentication> {
    private String id;
    private String name;
    private String url;
    private int authentionType;
    private String parentId;
    private String icon;
    @Override
    public Authentication toDomain() {
        Authentication authentication = new Authentication();
        authentication.setMenuId(id);
        authentication.setName(name);
        authentication.setUrl(url);
        authentication.setAuthentionType(authentionType);
        if (StringUtils.isNotEmpty(parentId)) {
            authentication.setParentId(parentId);
        }
        return authentication;
    }

    @Override
    public Command<Authentication> fromDomain(Authentication domain) {
        this.id = domain.getMenuId();
        this.name = domain.getName();
        this.url = domain.getUrl();
        this.authentionType = domain.getAuthentionType();
        this.parentId = domain.getParentId();
        this.icon = domain.getIcon();
        return this;
    }
}