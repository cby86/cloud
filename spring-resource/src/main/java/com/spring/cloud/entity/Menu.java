package com.spring.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
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
    @JsonIgnore
    private Menu parent;

    /**
     * 子集
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private List<Menu> children;

    /**
     * 类型 0：菜单 1：功能
     */
    @Enumerated(value = EnumType.STRING)
    private MenuType menuType;

    private String url;

    private String icon;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinTable(name="mb_hunter_menu_resource",
            joinColumns={@JoinColumn(name="menu_id")},
            inverseJoinColumns={@JoinColumn(name="resource_id")}
    )
    @JsonIgnore
    private List<Resource> resources;

    public boolean isMenu() {
        return menuType.equals(MenuType.Menu);
    }
}
