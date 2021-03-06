package com.spring.cloud.controller;

import com.spring.cloud.base.BaseController;
import com.spring.cloud.controller.command.MenuCommand;
import com.spring.cloud.controller.command.ResourceCommand;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.entity.MenuType;
import com.spring.cloud.entity.Resource;
import com.spring.cloud.service.MenuService;
import com.spring.cloud.service.ResourceService;
import com.spring.cloud.support.mvc.ResourceDesc;
import com.spring.cloud.utils.CommandUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 11:24
 **/
@RestController
@RequestMapping("/menu")
@Api(description = "菜单管理")
public class MenuController extends BaseController {

    @Autowired
    MenuService menuService;

    @Autowired
    ResourceService resourceService;

    @RequestMapping(value = "/updateMenus",method = RequestMethod.POST)
    @ResourceDesc(model = "菜单管理", name = "菜单编辑", desc = "菜单编辑")
    @ApiOperation(value = "添加或者修改菜单",notes = "添加或者修改菜单")
    @ApiImplicitParam(name="menuCommand",value = "菜单数据对象",required = true,dataTypeClass = MenuCommand.class)
    public Map<String, Object> updateMenus(MenuCommand menuCommand) {
        Menu menu = menuCommand.toDomain();
        menuService.saveOrUpdate(menu);
        return this.resultMap(null);
    }

    @RequestMapping(value = "/findMenuById",method = {RequestMethod.POST,RequestMethod.GET})
    @ResourceDesc(model = "菜单管理", name = "查询菜单", desc = "根据ID查询菜单")
    @ApiOperation(value = "根据ID查询菜单",notes = "根据ID查询菜单")
    @ApiImplicitParam(name="menuId",value = "菜单ID",required = true,dataTypeClass =String.class)
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
    public Map<String, Object> findMenuByParentId(String parentId, String name, String url, MenuType menuType, String excludeMenuId) {
        List<Menu> menuList = menuService.findMenuByParentId(parentId,name,url,menuType,excludeMenuId);
        return this.resultMap(CommandUtils.toCommands(menuList,MenuCommand.class));
    }

    @RequestMapping("/findMenus")
    @ResourceDesc(model = "菜单管理", name = "分页查询菜单", desc = "分页查询给定条件菜单")
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

    @RequestMapping("/findBindResource")
    @ResourceDesc(model = "菜单管理", name = "查看绑定资源", desc = "分页查询绑定资源")
    public Map<String, Object> findBindResource(String appName,String name, String url, String menuId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Resource> resourcePageList = resourceService.findBindResource(appName,name, url, menuId, pageable);
        return this.resultMap(CommandUtils.responsePage(resourcePageList.getTotalElements(), resourcePageList.getTotalPages(),
                CommandUtils.toCommands(resourcePageList.getContent(), ResourceCommand.class)));
    }

    @RequestMapping("/unBindResource")
    @ResourceDesc(model = "菜单管理", name = "删除资源绑定", desc = "删除资源绑定")
    public Map<String, Object> unBindResource(String menuId,String [] resourceIds) {
        menuService.unBindResource(menuId,resourceIds);
        return this.resultMap(true);
    }

    @RequestMapping("/bindResources")
    @ResourceDesc(model = "菜单管理", name = "资源绑定", desc = "资源绑定")
    public Map<String, Object> bindResources(String menuId,@RequestParam(value = "resourceIds") List<String> resourceIds) {
        menuService.bindResources(menuId,resourceIds);
        return this.resultMap(true);
    }

}
