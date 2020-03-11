package com.spring.cloud.support.mvc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


public class WebRequestMappingConfig {
    @Bean
    @ConditionalOnBean(ResourceRegister.class)
    WebMvcRegistrations getWebMvcRegistrations(ResourceRegister resourceRegister) {
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                CustomRequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
                handlerMapping.setOrder(0);
                handlerMapping.setResourceRegister(resourceRegister);
                return handlerMapping;
            }
        };
    }
}
