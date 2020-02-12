package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.App;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppCommand implements Command<App> {
    private String id;
    private String name;
    private String description;
    private boolean hasChildren = true;
    @Override
    public App toDomain() {
        throw new UnsupportedOperationException("不支持的操作");
    }

    @Override
    public Command<App> fromDomain(App domain) {
        this.id = domain.getId();
        this.name = domain.getName();
        this.description = domain.getDescription();
        return this;
    }
}
