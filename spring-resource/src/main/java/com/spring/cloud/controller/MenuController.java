package com.spring.cloud.controller;

import com.spring.cloud.base.BaseController;
import com.spring.cloud.base.utils.PageUtils;
import com.spring.cloud.controller.command.MenuCommand;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.service.MenuService;
import com.spring.cloud.service.ResourceService;
import com.spring.cloud.utils.CommandUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 11:24
 **/
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    MenuService menuService;

    @Autowired
    ResourceService resourceService;

    @RequestMapping("/updateMenus")
    public Map<String, Object> updateMenus(MenuCommand menuCommand) {
        Menu menu = menuCommand.toDomain();
        menuService.saveMenu(menu);
        return this.resultMap(null);
    }

    @RequestMapping("/findMenuById")
    public Map<String, Object> findMenuById(String menuId) {
        Menu menu = menuService.findMenuById(menuId);
        return this.resultMap(new MenuCommand().fromDomain(menu));
    }

    @RequestMapping("/findMenus")
    public Map<String, Object> findMenus(String name, String url, int menuType, Integer page, Integer pageSize) {
        Pageable pageable = PageUtils.pageable(page, pageSize);
        Page<Menu> menuPageList = menuService.findMenuPageList(name, url, menuType, pageable);
        return this.resultMap(CommandUtils.responsePage(menuPageList.getTotalElements(), menuPageList.getTotalPages(),
                CommandUtils.toCommands(menuPageList.getContent(), MenuCommand.class)));
    }


}
