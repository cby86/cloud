package com.spring.cloud.message;

import org.springframework.context.ApplicationEvent;

public class MessageApplicationEvent extends ApplicationEvent{
    String messageType;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MessageApplicationEvent(Object source,String messageType) {
        super(source);
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }
}
