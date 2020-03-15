package com.spring.cloud.config.resource.endpoint;

import com.spring.cloud.config.CustomerReactiveAuthorizationManager;
import com.spring.cloud.support.ResourceRemoteApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationEventPublisher;

/**
 *  默认开始端点
 *  management:
 *    endpoint:
 *    resource-permit:
 *    enabled: false    禁用断点
 */
@Endpoint(id="resource-permit")
public class ResourcePermitRefreshMvcEndpoint {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ApplicationEventPublisher context;

    private String appId;


    public ResourcePermitRefreshMvcEndpoint(ApplicationEventPublisher context, String appId) {
        this.context = context;
        this.appId = appId;
    }

    @Autowired(required = false)
    CustomerReactiveAuthorizationManager customerReactiveAuthorizationManager;

    @ReadOperation
    public String resourcePermitInfo() {
        if (logger.isDebugEnabled()) {
            logger.debug("获取权限配置信息错误");
        }
        if (customerReactiveAuthorizationManager != null) {
            return customerReactiveAuthorizationManager.toString();
        }
        return null;
    }

    @WriteOperation
    public String refresh(@Selector String destination) {
        context.publishEvent(new ResourceRemoteApplicationEvent(this,appId,destination));
        return "success";
    }
}
