package com.spring.cloud.endpoint;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqMessageEndpointConfig {
    @Bean
    @ConditionalOnEnabledEndpoint()
    public MqMessageEndpoint getMqMessageEndpoint(ApplicationContext context) {
        return new MqMessageEndpoint();
    }
}
