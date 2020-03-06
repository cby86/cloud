package com.spring.cloud.config;

import com.rabbitmq.client.Channel;
import com.spring.cloud.entity.Event;
import com.spring.cloud.message.MessageApplicationEvent;
import com.spring.cloud.service.MessageService;
import com.spring.cloud.service.EventService;
import com.spring.cloud.utils.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;


@ConditionalOnProperty(value = "spring.mq.consumer.reliability.enabled",havingValue = "true",matchIfMissing = false)
@EnableBinding(Sink.class)
@Configuration
@EnableScheduling
public class InMessageConfig {
    Logger logger = LoggerFactory.getLogger(InMessageConfig.class);
    private final static int errorEventFetchSize = 100;
    private static final int maxRetry = 3;
    @Autowired(required = false)
    MessageService consumerService;
    @Autowired
    EventService eventService;
    @Autowired
    private RedisLock redisLock;

    @StreamListener(target = Sink.INPUT)
    void message(@Payload Object message, @Header(MessageApplicationEvent.messageTypeHeader) String messageType, @Header(MessageApplicationEvent.eventHeader) String eventId, @Header(AmqpHeaders.CHANNEL) Channel channel,
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

    @Scheduled(cron = "0/30 * * * * *")
    public void work() {
        String requestId = UUID.randomUUID().toString();
        boolean alarmNotify = redisLock.lock("eventConsumerLock", requestId, 10000);
        if (alarmNotify) {
            try {
                doRetryConsumerMessage(0, errorEventFetchSize);
                doClearConsumerMessage();
            } finally {
                redisLock.unlock("eventConsumerLock", requestId);
            }
        }
    }

    private void doRetryConsumerMessage(int page, int size) {
        Page<Event> events = eventService.loadConsumerEvents(page, size);
        for (Event event : events.getContent()) {
            try {
                consumerService.doProcess(event.getSourceId(),event.getPayload(),event.getEventType());
            }catch (Exception ex){
                event.setReason(ex.getMessage());
                if (logger.isErrorEnabled()) {
                    logger.error("发送消息错误{}:{}", event.getId(), ex.getMessage());
                }
            }finally {
                eventService.retryUpdate(event.getId(),event.getReason());
            }
        }
        if (events.getTotalPages() > 0 && events.getTotalPages() - 1 > page) {
            this.doRetryConsumerMessage(++page, size);
        }
    }

    /**
     * 清除处理成功消息
     */
    private void doClearConsumerMessage() {
        eventService.clearConsumerMessage();
    }
}
