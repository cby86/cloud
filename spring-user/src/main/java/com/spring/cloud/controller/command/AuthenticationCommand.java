package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.Authentication;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationCommand implements Command<Authentication> {
    private String id;
    private String name;
    private String url;
    private int authentionType;
    @Override
    public Authentication toDomain() {
        Authentication authentication = new Authentication();
        authentication.setMenuId(id);
        authentication.setName(name);
        authentication.setUrl(url);
        authentication.setAuthentionType(authentionType);
        return authentication;
    }

    @Override
    public Command<Authentication> fromDomain(Authentication domain) {
        this.id = domain.getMenuId();
        this.name = domain.getName();
        this.url = domain.getUrl();
        this.authentionType = domain.getAuthentionType();
        return this;
    }
}
