package com.spring.cloud.service.feign;

import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 13:46
 **/
public class MenuFallBack implements MenuService {
    @Override
    public Map<String, Object> saveMenu(String id, String name, int menuType, String url, String parentId) {
        return null;
    }
}
