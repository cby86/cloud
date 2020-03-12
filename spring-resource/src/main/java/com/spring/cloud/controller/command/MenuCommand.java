package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.entity.MenuType;
import com.spring.cloud.exception.BusinessException;
import com.spring.cloud.service.MenuService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuCommand implements Command<Menu> {
    private String id;
    private String menuName;
    private String url;
    private MenuType menuType;

    private String parentId;

    private String parentName;

    private boolean leaf;

    private String icon;

    private String code;

    private int sort;

    private List<String> resources;


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Autowired
    private MenuService menuService;

    @Override
    public Menu toDomain() {
        Menu menu = null;
        if (StringUtils.isNotEmpty(id)) {
            menu = menuService.findMenuById(id);
            if (MenuType.Function.equals(menuType) && menu.isMenu()) {
                throw new BusinessException("菜单不能修改为功能");
            }
        } else {
            menu = new Menu();
        }
        menu.setName(menuName);
        menu.setMenuType(menuType);
        menu.setUrl(url);
        menu.setIcon(icon);
        menu.setCode(code);
        menu.setSort(sort);
        if (menuService.hasSame(id,"code",code)) {
            throw new BusinessException("编码必须唯一");
        }
        if (StringUtils.isNotEmpty(this.parentId)) {
            menu.setParent(menuService.findMenuById(this.parentId));
        }else {
            menu.setParent(null);
        }
        return menu;
    }

    @Override
    public Command<Menu> fromDomain(Menu domain) {
        this.id = domain.getId();
        this.menuName = domain.getName();
        this.url = domain.getUrl();
        this.menuType = domain.getMenuType();
        this.icon = domain.getIcon();
        if (domain.getParent() != null) {
            this.parentId = domain.getParent().getId();
            this.parentName = domain.getParent().getName();
        }
        this.leaf = domain.getChildren().isEmpty();
        this.code = domain.getCode();
        this.sort = domain.getSort();
        if (!CollectionUtils.isEmpty(domain.getResources())) {
            resources = new ArrayList<>();
            domain.getResources().forEach(resource -> resources.add(resource.getUrl()));
        }
        return this;
    }



}
