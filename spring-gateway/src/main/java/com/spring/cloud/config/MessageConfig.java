package com.spring.cloud.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@EnableBinding(Sink.class)
public class MessageConfig {
    private static final int maxRetry = 3;
    @Autowired(required = false)
    private CustomerReactiveAuthorizationManager customerReactiveAuthorizationManager;

    @StreamListener(target = Sink.INPUT)
    void message(@Payload Object message, @Header(AmqpHeaders.CHANNEL) Channel channel,
                 @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, @Header("deliveryAttempt") int deliveryAttempt) throws Exception {

        try {
            if (customerReactiveAuthorizationManager != null) {
                customerReactiveAuthorizationManager.refresh();
            }
            //手动确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception ex) {
            if (maxRetry == deliveryAttempt) {
                channel.basicNack(deliveryTag, false, false);
            } else {
                throw ex;
            }
        }
    }

    @Bean
    ConnectionNameStrategy getCustomerConnectionNameStrategy(Environment environment) {
        return (factory) -> environment.getProperty("spring.application.name")+":"+environment.getProperty("server.port");
    }
}
