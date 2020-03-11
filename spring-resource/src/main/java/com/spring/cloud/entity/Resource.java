package com.spring.cloud.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 系统所有资源注册存储表
 * 对所有标记了
 */
@Entity
@Table(name = "mb_hunter_resource")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Resource extends BaseEntity {
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id")
    private App app;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="mb_hunter_menu_resource",
            joinColumns={@JoinColumn(name="resource_id")},
            inverseJoinColumns={@JoinColumn(name="menu_id")},
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    @JsonIgnore
    private List<Menu> menus;

    public Resource() {
    }

    public Resource(String url, String model, String name, String desc, String version) {
        this.url = url;
        this.model = model;
        this.name = name;
        this.description = desc;
        this.versionNumber = version;
    }

}
