package com.spring.cloud.config;

import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MessageConfig {
    @Bean
    ConnectionNameStrategy getCustomerConnectionNameStrategy(Environment environment) {
        return (factory) -> environment.getProperty("spring.application.name")+":"+environment.getProperty("server.port");
    }
}
