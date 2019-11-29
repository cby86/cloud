package com.spring.cloud.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mb_hunter_authentication")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Authentication extends BaseEntity {
    /**
     * 菜单ID
     */
    private String menuId;
    /**
     * 菜单名称
     */
    private String menuName;

    /**
     *  0 ：菜单授权；1资源授权
     */
    private int authentionType;
    /**
     * 应用名称
     */
    private String appName;
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

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    public int getAuthentionType() {
        return authentionType;
    }

    public void setAuthentionType(int authentionType) {
        this.authentionType = authentionType;
    }
}
