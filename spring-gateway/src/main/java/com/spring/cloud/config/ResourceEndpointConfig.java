package com.spring.cloud.config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(CustomerReactiveAuthorizationManager.class)
public class ResourceEndpointConfig{
    @Bean
    public ResourcePermitRefreshMvcEndpoint getResourcePermitManager(CustomerReactiveAuthorizationManager customerReactiveAuthorizationManager) {
        return new ResourcePermitRefreshMvcEndpoint(customerReactiveAuthorizationManager);
    }
}
