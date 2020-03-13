package com.spring.cloud.support.mvc;

import com.spring.cloud.global.ResourceRegister;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


public class WebRequestMappingConfig {
    @Bean
    @ConditionalOnBean(value = ResourceRegister.class)
    WebMvcRegistrations getWebMvcRegistrations(ResourceRegister resourceRegister) {
        CustomRequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping(resourceRegister);
        return new WebMvcRegistrations() {
            @Override
            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
                return handlerMapping;
            }
        };
    }

    @Bean
    @ConditionalOnBean(WebMvcRegistrations.class)
    ApplicationRunner afterStart(WebMvcRegistrations webMvcRegistrations) {
        RequestMappingHandlerMapping requestMappingHandlerMapping = webMvcRegistrations.getRequestMappingHandlerMapping();
        if (requestMappingHandlerMapping instanceof CustomRequestMappingHandlerMapping) {
            CustomRequestMappingHandlerMapping customRequestMappingHandlerMapping = (CustomRequestMappingHandlerMapping) requestMappingHandlerMapping;
            return args -> customRequestMappingHandlerMapping.doRegister();
        }
        return args -> {};
    }
}
