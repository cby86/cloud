package com.spring.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author panyuanjun
 * @Date 2019/11/8/008 14:43
 **/
@Entity
@Table(name = "mb_hunter_menu")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Menu extends BaseEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 父级
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Menu parent;

    /**
     * 子集
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private List<Menu> children;

    /**
     * 类型 0：菜单 1：功能
     */
    private int menuType;

    private String url;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinTable(name="mb_hunter_menu_resource",
            joinColumns={@JoinColumn(name="menu_id")},
            inverseJoinColumns={@JoinColumn(name="resource_id")}
    )
    private List<Resource> resources;


    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }


    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public void addAllResource(List<Resource> resourceList) {
        if (resources == null) {
            resources = new ArrayList<>();
        }
        resources.addAll(resourceList);
    }
}
