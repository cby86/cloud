package com.spring.cloud.config;

import com.spring.cloud.entity.Event;
import com.spring.cloud.message.MessageApplicationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.domain.Page;
import org.springframework.integration.amqp.support.ReturnedAmqpMessageException;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


public class OutMessageConfig extends BaseMessageConfig {
    private final static String lockName = "_eventSendLock";

    @Autowired(required = false)
    @Output(Source.OUTPUT)
    private MessageChannel out;

    public String getLockName() {
        return lockName;
    }


    /**
     * 生产者发送消息错误处理，消息没有成功
     *
     * @param message
     */
    @StreamListener("errorChannel")
    public void error(Message<?> message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        if (errorMessage.getPayload() instanceof ReturnedAmqpMessageException) {
            ReturnedAmqpMessageException payload = (ReturnedAmqpMessageException) errorMessage.getPayload();
            org.springframework.amqp.core.Message amqpMessage = payload.getAmqpMessage();
            MessageProperties messageProperties = amqpMessage.getMessageProperties();
            Object event = messageProperties.getHeaders().get(MessageApplicationEvent.eventHeader);
            if (event != null) {
                eventService.errorToSendEventMessage(event,payload.getReplyText());
            }
        }
    }

    /**
     * 消息接收确认
     * @param message
     * @param event
     */
    @ServiceActivator(inputChannel = "bussinessMessageConfirm")
    public void confirm(Message<?> message, @Header(MessageApplicationEvent.eventHeader) Object event) {
        eventService.successToSendEvent(event);
    }


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onApplicationEvent(MessageApplicationEvent event) {
        try {
            sendMessage(event);
        } catch (Exception ex) {
            if (logger.isErrorEnabled()) {
                logger.error("发送消息错误{}:{}", event.getEvent(), ex.getMessage());
            }
            eventService.errorToSendEventMessage(event.getEvent(),ex.getMessage());
        }
    }

    private void sendMessage(MessageApplicationEvent event) {
        Message<Object> msg = MessageBuilder.withPayload(event.getSource()).setHeader(MessageApplicationEvent.eventHeader, event.getEvent())
                .setHeader(MessageApplicationEvent.messageTypeHeader, event.getMessageType())
                .build();
        out.send(msg);
    }

    @Override
    public void redoMessage() {
        doRetrySendMessage(0, errorEventFetchSize);
        doClearSendMessage();
    }

    /**
     * 清除处理成功消息
     */
    private void doClearSendMessage() {
        eventService.clearSendMessage();
    }

    private void doRetrySendMessage(int page, int size) {
        Page<Event> events = eventService.loadProducerEvent(page, size);
        for (Event event : events.getContent()) {
            try {
                event.setMarkerError(false);
                event.increaseRetry();
                eventService.save(event);
                this.sendMessage(new MessageApplicationEvent(event.getPayload(), event.getEventType()).bindEvent(event.getId()).bindSource(event.getSourceId()));
            }catch (Exception ex){
                if (logger.isErrorEnabled()) {
                    logger.error("发送消息错误{}:{}", event.getId(), ex.getMessage());
                }
            }
        }
        if (events.getTotalPages() > 0 && events.getTotalPages() - 1 > page) {
            this.doRetrySendMessage(++page, size);
        }
    }

}
