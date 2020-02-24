package com.spring.cloud.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mb_hunter_authentication")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Authentication extends BaseEntity {
    /**
     * 资源ID
     */
    private String menuId;
    /**
     * 资源名称
     */
    private String name;

    /**
     *  0 ：菜单授权；功能授权
     */
    private int authentionType;

    /**
     * 路径
     */
    private String url;

    /**
     * 权限内部关系
     */
    private String parentId;


    private String icon;

    private String code;
    private int sort;



}
