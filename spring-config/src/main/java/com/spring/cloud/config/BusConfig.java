package com.spring.cloud.config;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class BusConfig {
    @Bean
    public BusRefreshListener refreshListener(ContextRefresher contextRefresher) {
        return new BusRefreshListener(contextRefresher);
    }
    @Bean
    ConnectionNameStrategy getCustomerConnectionNameStrategy(Environment environment) {
        return (factory) -> environment.getProperty("spring.application.name")+":"+environment.getProperty("server.port");
    }
}
