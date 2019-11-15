package com.spring.cloud.config;
import com.alibaba.fastjson.JSONObject;
import com.spring.cloud.service.feign.ResourceService;
import com.spring.cloud.support.mvc.CustomRequestMappingHandlerMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class WebRequestMappingConfig implements WebMvcRegistrations {
    @Autowired
    private ResourceService resourceService;

    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        CustomRequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
        handlerMapping.setOrder(0);
        handlerMapping.setResourceRegister(m->resourceService.register(JSONObject.toJSONString(m)));
        return handlerMapping;
    }
}
