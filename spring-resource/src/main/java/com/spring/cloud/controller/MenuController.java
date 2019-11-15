package com.spring.cloud.controller;

import com.spring.cloud.base.BaseController;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @RequestMapping("/saveMenu")
    public Map<String, Object> saveMenu(String id, String name, int menuType, String url, String parentId) {

        menuService.saveMenu(id, name, menuType, url, parentId);

        return this.resultMap("0", "success", null);
    }

    @RequestMapping("/findMenus")
    public Map<String, Object> findMenus() {
        List<Menu> menuList = menuService.findMenuList(0);
        return this.resultMap("0", "success", menuList);
    }

    @RequestMapping("/register")
    public Map<String, Object> register(@RequestBody  Map<String,List<Map>> resource) {
        List<Menu> menus = new ArrayList<>();
        for (Map.Entry<String, List<Map>> entry : resource.entrySet()) {
            String url = entry.getKey();
            List<Map> desc = entry.getValue();
            Menu menu = new Menu();
            for (Map<String, String> data : desc) {
                String model = data.get("model");
                if (StringUtils.isEmpty(model)) {
                    menu.setName(model);
                    menu.setMenuType(0);
                }
                String name = data.get("name");
                String desc1 = data.get("desc");
                String version = data.get("version");
            }

        }
        return this.resultMap("0", "success", null);
    }

}
