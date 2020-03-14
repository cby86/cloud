package com.spring.cloud.config;

import com.rabbitmq.client.Channel;
import com.spring.cloud.entity.Event;
import com.spring.cloud.message.MessageApplicationEvent;
import com.spring.cloud.service.MessageService;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Optional;


public class InMessageConfig extends BaseMessageConfig {
    private final static String lockName = "_eventConsumerLock";
    @Autowired(required = false)
    protected MessageService consumerService;

    public String getLockName() {
        return lockName;
    }

    @StreamListener(target = Sink.INPUT)
    void message(@Payload Object message, @Header(MessageApplicationEvent.messageTypeHeader) String messageType,
                 @Header(MessageApplicationEvent.messageSourceIdHeader) String sourceId,
                 @Header(MessageApplicationEvent.eventHeader) String eventId, @Header(AmqpHeaders.CHANNEL) Channel channel,
                 @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, @Header("deliveryAttempt") int deliveryAttempt) throws Exception {
        try {
            if (consumerService != null) {
                Optional<Integer> event = eventService.check(eventId, sourceId, message, messageType);
                if (event.isPresent()) {
                    consumerService.doProcess(event.get(),sourceId, message, messageType);
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

    @Override
    public void redoMessage() {
        doRetryConsumerMessage(0, errorEventFetchSize);
        doClearConsumerMessage();
    }

    private void doRetryConsumerMessage(int page, int size) {
        Page<Event> events = eventService.loadConsumerEvents(page, size);
        for (Event event : events.getContent()) {
            try {
                consumerService.doProcess(event.getId(),event.getSourceId(), event.getPayload(), event.getEventType());
            } catch (Exception ex) {
                event.setReason(ex.getMessage());
                if (logger.isErrorEnabled()) {
                    logger.error("发送消息错误{}:{}", event.getId(), ex.getMessage());
                }
            } finally {
                eventService.retryUpdate(event.getId(), event.getReason());
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
