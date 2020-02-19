package com.spring.cloud.controller;

import com.spring.cloud.base.BaseController;
import com.spring.cloud.controller.command.MenuCommand;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.service.MenuService;
import com.spring.cloud.service.ResourceService;
import com.spring.cloud.support.mvc.ResourceDesc;
import com.spring.cloud.utils.CommandUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    @ResourceDesc(model = "菜单管理", name = "菜单编辑", desc = "菜单编辑")
    public Map<String, Object> updateMenus(MenuCommand menuCommand) {
        Menu menu = menuCommand.toDomain();
        menuService.saveMenu(menu);
        return this.resultMap(null);
    }

    @RequestMapping("/findMenuById")
    @ResourceDesc(model = "菜单管理", name = "查询菜单", desc = "根据ID查询菜单")
    public Map<String, Object> findMenuById(String menuId) {
        Menu menu = menuService.findMenuById(menuId);
        return this.resultMap(new MenuCommand().fromDomain(menu));
    }

    @RequestMapping("/deletedMenu")
    @ResourceDesc(model = "菜单管理", name = "删除菜单", desc = "根据ID删除菜单")
    public Map<String, Object> deletedMenu(String menuId) {
        menuService.deletedMenu(menuId);
        return this.resultMap(true);
    }

    @RequestMapping("/findMenuByParentId")
    @ResourceDesc(model = "菜单管理", name = "查询子菜单", desc = "根据父级菜单ID查询菜单")
    public Map<String, Object> findMenuByParentId(String parentId,String name,String url,String excludeMenuId) {
        List<Menu> menuList = menuService.findMenuByParentId(parentId,name,url,excludeMenuId);
        return this.resultMap(CommandUtils.toCommands(menuList,MenuCommand.class));
    }

    @RequestMapping("/findMenus")
    @ResourceDesc(model = "菜单管理", name = "分页查询菜单", desc = "分页查询菜单")
    public Map<String, Object> findMenus(String name, String url, int menuType, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Menu> menuPageList = menuService.findMenuPageList(name, url, menuType, pageable);
        return this.resultMap(CommandUtils.responsePage(menuPageList.getTotalElements(), menuPageList.getTotalPages(),
                CommandUtils.toCommands(menuPageList.getContent(), MenuCommand.class)));
    }

    @RequestMapping("/findAllMenu")
    @ResourceDesc(model = "菜单管理", name = "查询所有菜单", desc = "查询所有菜单")
    public Map<String, Object> findAllMenu() {
        List<Menu> menuList = menuService.findAllMenu();
        return this.resultMap(CommandUtils.toCommands(menuList, MenuCommand.class));
    }

}
