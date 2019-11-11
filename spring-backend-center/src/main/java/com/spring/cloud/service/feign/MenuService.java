package com.spring.cloud.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 13:46
 **/
@FeignClient(value = "spring-resource",fallback = MenuFallBack.class)
public interface MenuService {


    @RequestMapping(value = "/saveMenu", method = RequestMethod.POST, produces = "application/json")
    Map<String, Object> saveMenu(String id, String name, int menuType, String url, String parentId);

}
