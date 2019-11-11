package com.spring.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;

import javax.persistence.*;
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
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Menu> children;

    /**
     * 类型 0：菜单 1：功能
     */
    private int menuType;

    private String url;

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
}
