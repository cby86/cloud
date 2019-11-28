package com.spring.cloud.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统所有资源注册存储表
 * 对所有标记了
 */
@Entity
@Table(name = "mb_hunter_resource")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Resource extends BaseEntity {
    private String app;
    /**
     * 模块
     */
    private String model;
    /**
     * 功能名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;

    /**
     * 路径
     */
    private String url;

    /**
     * 版本
     */
    private String versionNumber;

    public Resource() {
    }

    public Resource(String url, String model, String name, String desc, String version) {
        this.url = url;
        this.model = model;
        this.name = name;
        this.description = desc;
        this.versionNumber = version;
    }


    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }
}
