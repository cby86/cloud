package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.Authentication;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.List;

@Getter
@Setter
public class AuthenticationCommand implements Command<Authentication> {
    private String id;
    private String name;
    private String url;
    private int authentionType;
    private String parentId;
    private String icon;
    private String code;
    private List<String> details;
    private int sort;
    private int forPrivate;
    @Override
    public Authentication toDomain() {
        Authentication authentication = new Authentication();
        authentication.setMenuId(id);
        authentication.setName(name);
        authentication.setUrl(url);
        authentication.setAuthentionType(authentionType);
        authentication.setCode(code);
        authentication.setIcon(icon);
        authentication.setSort(sort);
        authentication.setForPrivate(this.forPrivate);
        if (StringUtils.isNotEmpty(parentId)) {
            authentication.setParentId(parentId);
        }
        if (details != null) {
            details.forEach(systemResource->authentication.addAuthenticationDetails(systemResource));
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
        this.forPrivate = domain.getForPrivate();
        this.icon = domain.getIcon();
        this.code = domain.getCode();
        return this;
    }
}
