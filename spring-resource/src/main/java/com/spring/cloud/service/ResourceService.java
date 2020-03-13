package com.spring.cloud.service;

import com.spring.cloud.entity.Resource;
import com.spring.cloud.global.ResourceRegister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResourceService extends ResourceRegister {

    void resourceDeleteById(String resourceId);

    Resource findResourceById(String resourceId);

    void saveResource(Resource resource);

    Page<Resource> findResourcePageList(String appName, String name, String url, Pageable pageable);

    Page<Resource> findBindResource(String appName, String name, String url, String menuId, Pageable pageable);
}
