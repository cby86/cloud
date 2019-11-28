package com.spring.cloud.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 16:05
 **/
@FeignClient(value = "spring-resource",fallback = ResourceFailBack.class)
public interface ResourceService {

    @RequestMapping(value = "/role/findRoles", method = RequestMethod.POST, produces = "application/json")
    Map<String, Object> findRoles();


    @RequestMapping(value = "/menu/saveMenu", method = RequestMethod.POST, produces = "application/json")
    Map<String, Object> saveMenu(@RequestParam(value = "id") String id, @RequestParam(value = "name") String name,
                                 @RequestParam(value = "menuType") int menuType, @RequestParam(value = "url") String url,
                                 @RequestParam(value = "parentId") String parentId);

    @RequestMapping(value = "/menu/findMenus", method = RequestMethod.POST, produces = "application/json")
    Map<String, Object> findMenus();

    @RequestMapping(value = "/resource/register", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    void register(@RequestBody List<String> resource);
}
