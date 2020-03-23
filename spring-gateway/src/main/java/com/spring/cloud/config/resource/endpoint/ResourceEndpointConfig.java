package com.spring.cloud.config.resource.endpoint;

import com.spring.cloud.config.CustomerReactiveAuthorizationManager;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(CustomerReactiveAuthorizationManager.class)
@RemoteApplicationEventScan(basePackages = "com.spring.cloud.support")
public class ResourceEndpointConfig{
    @Bean
    @ConditionalOnEnabledEndpoint()
    public ResourcePermitRefreshMvcEndpoint getResourcePermitManager(ApplicationContext context, BusProperties busProperties) {
        return new ResourcePermitRefreshMvcEndpoint(context,busProperties.getId());
    }
}
