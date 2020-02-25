package com.spring.cloud.config;

import com.spring.cloud.message.IMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuMessage implements IMessage{
    private String id;
    private String menuName;
    private String url;
    private String menuType;

    private String parentId;

    private String parentName;

    private boolean leaf;

    private String icon;

    private String code;

    private int sort;
}
