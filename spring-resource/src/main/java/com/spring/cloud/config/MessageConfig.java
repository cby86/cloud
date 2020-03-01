package com.spring.cloud.config;

import com.spring.cloud.message.MessageListener;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.support.ReturnedAmqpMessageException;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ErrorMessage;

@Configuration
@EnableBinding(Source.class)
public class MessageConfig {
    @Bean
    MessageListener messageListener(@Output(Source.OUTPUT) MessageChannel channel) {
        return new MessageListener(channel);
    }

    /**
     * 生产者发送消息错误处理，消息没有成功
     *
     * @param message
     */
    @StreamListener("errorChannel")
    public void error(Message<?> message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        System.out.println(((ReturnedAmqpMessageException) errorMessage.getPayload()).getAmqpMessage());
    }

    @ServiceActivator(inputChannel = "bussinessMessageConfirm")
    public void confirm(Object message) {
        System.out.println(message);
    }

}
