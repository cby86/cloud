package com.spring.cloud.config;
import com.spring.cloud.endpoint.ResourcePermitRefreshMvcEndpoint;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ConditionalOnClass(CustomerReactiveAuthorizationManager.class)
public class ResourceEndpointConfig{
    @Bean
    @ConditionalOnEnabledEndpoint()
    public ResourcePermitRefreshMvcEndpoint getResourcePermitManager(ApplicationContext context,Environment environment) {
        return new ResourcePermitRefreshMvcEndpoint(context,environment.getProperty("spring.application.name"));
    }
}
