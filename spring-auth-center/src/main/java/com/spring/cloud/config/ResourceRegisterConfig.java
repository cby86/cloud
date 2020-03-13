package com.spring.cloud.config;

import com.spring.cloud.global.ResourceDefine;
import com.spring.cloud.global.ResourceRegister;
import com.spring.cloud.service.feign.ResourceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 不
 */
@Component
public class ResourceRegisterConfig implements ResourceRegister {
    @Autowired
    private ResourceInterface resourceInterface;

    @Override
    public void registerEndpoint(List<ResourceDefine> endpoints) {
        resourceInterface.registerEndpoint(endpoints);
    }

    @Override
    public List<ResourceDefine> getCustomerResource(String app,String description) {
        List<ResourceDefine> customerResourceList = new ArrayList<ResourceDefine>();
        customerResourceList.add(new ResourceDefine(app, description, "/oauth/token", "授权中心", "登录", "登录", "0"));
        return customerResourceList;
    }
}
