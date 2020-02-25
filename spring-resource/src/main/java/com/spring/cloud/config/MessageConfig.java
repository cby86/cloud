package com.spring.cloud.config;

import com.spring.cloud.message.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableBinding(Source.class)
public class MessageConfig {
    @Bean
    MessageListener messageListener(@Output(Source.OUTPUT) MessageChannel channel) {
        return new MessageListener(channel);
    }
}
