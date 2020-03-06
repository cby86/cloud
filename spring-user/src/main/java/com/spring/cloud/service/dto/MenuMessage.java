package com.spring.cloud.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuMessage {
    private String id;
    private String name;
    private String url;
    private String menuType;

    private String parentId;

    private String parentName;

    private boolean leaf;

    private String icon;

    private String code;

    private int sort;
}
