package com.spring.cloud.config;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusConfig {
    @Bean
    public BusRefreshListener refreshListener(ContextRefresher contextRefresher) {
        return new BusRefreshListener(contextRefresher);
    }
}
