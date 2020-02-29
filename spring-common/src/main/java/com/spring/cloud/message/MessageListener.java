package com.spring.cloud.message;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;


public class MessageListener implements ApplicationListener<MessageApplicationEvent>{
    private MessageChannel out;
 
    public MessageListener(MessageChannel out) {
        this.out = out;
    }

    @Async
    @Override
    public void onApplicationEvent(MessageApplicationEvent event) {
        Message<Object> msg = MessageBuilder.withPayload(event.getSource()).setHeader("messageType", event.getMessageType())
                .build();
        out.send(msg);
    }
}
