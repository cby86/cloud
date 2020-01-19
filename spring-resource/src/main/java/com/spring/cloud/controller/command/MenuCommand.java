package com.spring.cloud.controller.command;

import com.spring.cloud.base.Command;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.service.MenuService;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class MenuCommand implements Command<Menu> {
    private String id;
    private String menuName;
    private String url;
    private int menuType;

    private String parentId;

    private String parentName;

    private boolean hasChildren;

    @Autowired
    MenuService menuService;

    @Override
    public Menu toDomain() {
        Menu menu = null;
        if (StringUtils.isNotEmpty(id)) {
            menu = menuService.findMenuById(id);
        } else {
            menu = new Menu();
        }
        menu.setName(menuName);
        menu.setMenuType(menuType);
        menu.setUrl(url);
        return menu;
    }

    @Override
    public Command<Menu> fromDomain(Menu domain) {
        this.id = domain.getId();
        this.menuName = domain.getName();
        this.url = domain.getUrl();
        this.menuType = domain.getMenuType();
        if (domain.getParent() != null) {
            this.parentId = domain.getId();
            this.parentName = domain.getName();
        }
        this.hasChildren = domain.getChildren().isEmpty();
        return this;
    }

}
