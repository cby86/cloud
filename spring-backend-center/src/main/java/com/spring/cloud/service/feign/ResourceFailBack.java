package com.spring.cloud.service.feign;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 16:06
 **/
@Component
public class ResourceFailBack implements ResourceService {
    @Override
    public Map<String, Object> findRoles() {
        return null;
    }

    @Override
    public Map<String, Object> saveMenu(String id, String name, int menuType, String url, String parentId) {
        return null;
    }

    @Override
    public Map<String, Object> findMenus() {
        return null;
    }

    @Override
    public void register(String resource) {

    }
}
