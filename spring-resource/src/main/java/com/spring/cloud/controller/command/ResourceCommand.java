package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.entity.MenuType;
import com.spring.cloud.entity.Resource;
import com.spring.cloud.exception.BusinessException;
import com.spring.cloud.service.ResourceService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class ResourceCommand implements Command<Resource> {
    private String id;
    private String name;
    private String model;
    private String description;
    private String url;
    private String versionNumber;
    private String appName;
    private String appDesc;
    @Autowired
    ResourceService resourceService;
    @Override
    public Resource toDomain() {
        Resource resource = null;
        if (StringUtils.isNotEmpty(id)) {
            resource = resourceService.findResourceById(id);
        }else {
            throw new BusinessException("资源不存在");
        }
        resource.setName(name);
        resource.setDescription(description);
        return resource;

    }

    @Override
    public Command<Resource> fromDomain(Resource domain) {
        this.id = domain.getId();
        this.description = domain.getDescription();
        this.model = domain.getModel();
        this.name = domain.getName();
        this.url = domain.getUrl();
        this.versionNumber = domain.getVersionNumber();
        if (domain.getApp() != null) {
            this.appName = domain.getApp().getName();
            this.appDesc = domain.getApp().getDescription();
        }
        return this;
    }
}
