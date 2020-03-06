package com.spring.cloud.config;

import com.rabbitmq.client.Channel;
import com.spring.cloud.message.MessageApplicationEvent;
import com.spring.cloud.service.MessageService;
import com.spring.cloud.service.EventService;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;


@ConditionalOnProperty(value = "spring.mq.consumer.reliability.enabled",havingValue = "true",matchIfMissing = false)
@EnableBinding(Sink.class)
@Configuration
public class InMessageConfig {
    private static final int maxRetry = 3;
    @Autowired(required = false)
    MessageService consumerService;
    @Autowired
    EventService eventService;

    @StreamListener(target = Sink.INPUT)
    void menuUpdate(@Payload Object message, @Header(MessageApplicationEvent.messageTypeHeader) String messageType, @Header(MessageApplicationEvent.eventHeader) String eventId, @Header(AmqpHeaders.CHANNEL) Channel channel,
                    @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, @Header("deliveryAttempt") int deliveryAttempt) throws Exception {

        try {
            if (consumerService != null) {
                if (eventService.allow(eventId, message, messageType)) {
                    consumerService.doProcess(eventId, message, messageType);
                }
            }
            //手动确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception ex) {
            if (maxRetry == deliveryAttempt) {
                channel.basicNack(deliveryTag, false, false);
                eventService.errorToConsumerEventMessage(eventId, ex.getMessage());
            } else {
                throw ex;
            }
        }

    }

}
