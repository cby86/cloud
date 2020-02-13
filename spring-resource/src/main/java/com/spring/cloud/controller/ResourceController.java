package com.spring.cloud.controller;

import com.spring.cloud.base.BaseController;
import com.spring.cloud.controller.command.ResourceCommand;
import com.spring.cloud.entity.Resource;
import com.spring.cloud.service.ResourceService;
import com.spring.cloud.support.mvc.ResourceDesc;
import com.spring.cloud.utils.CommandUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, Object> register(@RequestBody List<String> resource) {
        resourceService.register(resource);
        return this.resultMap(null);
    }

    @RequestMapping("/findResource")
    @ResourceDesc(model = "资源管理", name = "资源分页列表", desc = "资源分页列表")
    public Map<String, Object> findResource(String name, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Resource> appPageList = resourceService.findResourcePageList(name,pageable);
        return this.resultMap(CommandUtils.responsePage(appPageList.getTotalElements(), appPageList.getTotalPages(),
                CommandUtils.toCommands(appPageList.getContent(), ResourceCommand.class)));
    }

    @RequestMapping("/updateResource")
    @ResourceDesc(model = "资源管理", name = "修改资源", desc = "修改资源")
    public Map<String, Object> resourceDelete(ResourceCommand resourceCommand) {
        Resource resource = resourceCommand.toDomain();
        resourceService.saveResource(resource);
        return this.resultMap(null);
    }

    @RequestMapping("/deleteResource")
    @ResourceDesc(model = "资源管理", name = "删除资源", desc = "删除资源")
    public Map<String, Object> resourceDelete(String resourceId) {
        resourceService.resourceDeleteById(resourceId);
        return this.resultMap(true);
    }


    @RequestMapping("/findResourceById")
    @ResourceDesc(model = "资源管理", name = "查询资源", desc = "根据ID查询资源")
    public Map<String, Object> findResourceById(String resourceId) {
        Resource resource=resourceService.findResourceById(resourceId);
        return this.resultMap(new ResourceCommand().fromDomain(resource));
    }

}
