package com.spring.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mb_hunter_role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","fieldHandler"})
public class Role extends BaseEntity {

    private String name;

    private String code;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mb_hunter_role_menu_middle",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")})
    private List<Menu> menuList;


    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
