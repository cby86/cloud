package com.spring.cloud.support.mvc.endpoint;

import com.spring.cloud.support.mvc.CustomRequestMappingHandlerMapping;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(CustomRequestMappingHandlerMapping.class)
public class ResourceEndpointConfig {
    @Bean
    @ConditionalOnEnabledEndpoint()
    public ResourceRegisterEndpoint getResourcePermitManager(ApplicationContext context) {
        return new ResourceRegisterEndpoint();
    }
}
