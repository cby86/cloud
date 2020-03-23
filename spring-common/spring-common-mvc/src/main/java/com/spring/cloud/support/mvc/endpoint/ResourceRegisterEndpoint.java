package com.spring.cloud.support.mvc.endpoint;

import com.spring.cloud.global.ResourceDefine;
import com.spring.cloud.support.mvc.CustomRequestMappingHandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;

import java.util.List;

/**
 *  默认开始端点
 *  management:
 *    endpoint:
 *    resource-permit:
 *    enabled: false    禁用断点
 */
@Endpoint(id="resource-register")
public class ResourceRegisterEndpoint {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    WebMvcRegistrations webMvcRegistrations;

    @ReadOperation
    public  List<ResourceDefine> resourceDetailsInfo() {
        if (webMvcRegistrations != null) {
            CustomRequestMappingHandlerMapping requestMappingHandlerMapping = getCustomRequestMappingHandlerMapping();
            return requestMappingHandlerMapping.getResourceDefines();
        }
        return null;
    }

    private CustomRequestMappingHandlerMapping getCustomRequestMappingHandlerMapping() {
        return (CustomRequestMappingHandlerMapping) webMvcRegistrations.getRequestMappingHandlerMapping();
    }

    @WriteOperation
    public String register() {
        getCustomRequestMappingHandlerMapping().doRegister();
        return "success";
    }
}
