package com.spring.cloud.controller;

import com.spring.cloud.base.BaseController;
import com.spring.cloud.base.PageUtils;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.service.MenuService;
import com.spring.cloud.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/saveMenu")
    public Map<String, Object> saveMenu(String id, String name, int menuType, String url, String parentId) {

        menuService.saveMenu(id, name, menuType, url, parentId);

        return this.resultMap(null);
    }

    @RequestMapping("/findMenus")
    public Map<String, Object> findMenus(String menuName, int menuType,Integer page, Integer pageSize) {
        Pageable pageable = PageUtils.pageable(page, pageSize);
        Page<Menu> menuPageList = menuService.findMenuPageList(menuName, menuType, pageable);
        return this.resultMap(PageUtils.responsePage(menuPageList));
    }


}
