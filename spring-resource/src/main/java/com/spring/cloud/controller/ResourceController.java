package com.spring.cloud.controller;

import com.spring.cloud.base.BaseController;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.service.MenuService;
import com.spring.cloud.service.ResourceService;
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
@RequestMapping("/resource")
public class ResourceController extends BaseController {
    @Autowired
    ResourceService resourceService;
    @RequestMapping("/register")
    public Map<String, Object> register(@RequestBody  List<String> resource) {
        resourceService.register(resource);
        return this.resultMap( null);
    }

}
