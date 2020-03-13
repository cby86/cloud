package com.spring.cloud.global;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResourceDefine implements Serializable {
    private String app;
    private String description;
    private String url;
    private String model;
    private String name;
    private String desc;
    private String version;

    public ResourceDefine() {
    }

    public ResourceDefine(String app, String description, String url, String model, String name, String desc, String version) {
        this.app = app;
        this.description = description;
        this.url = url;
        this.model = model;
        this.name = name;
        this.desc = desc;
        this.version = version;
    }
}
