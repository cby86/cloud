package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.Resource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceCommand implements Command<Resource> {
    private String id;
    private String name;
    private String model;
    private String description;
    private String url;
    private String versionNumber;
    private boolean hasChildren = false;
    @Override
    public Resource toDomain() {
        return null;
    }

    @Override
    public Command<Resource> fromDomain(Resource domain) {
        this.id = domain.getId();
        this.description = domain.getDescription();
        this.model = domain.getModel();
        this.name = domain.getName();
        this.url = domain.getUrl();
        this.versionNumber = domain.getVersionNumber();
        return this;
    }
}
