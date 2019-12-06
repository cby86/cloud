package com.spring.cloud.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceEndpointConfig{
    @Bean
    public ResourcePermitRefreshMvcEndpoint getResourcePermitManager() {
        return new ResourcePermitRefreshMvcEndpoint();
    }
}
