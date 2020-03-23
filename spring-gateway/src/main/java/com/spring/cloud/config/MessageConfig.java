package com.spring.cloud.config;

import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Configurable
public class MessageConfig {
    @Bean
    ConnectionNameStrategy getCustomerConnectionNameStrategy(Environment environment) {
        return (factory) -> environment.getProperty("spring.application.name")+":"+environment.getProperty("server.port");
    }
}
