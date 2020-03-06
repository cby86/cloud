package com.spring.cloud.message;

import org.springframework.context.ApplicationEvent;

public class MessageApplicationEvent extends ApplicationEvent {
    public final static String eventHeader = "event";
    public final static String messageTypeHeader = "messageType";
    String messageType;
    Object event;
    String sourceId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MessageApplicationEvent(Object source, String messageType) {
        super(source);
        if (source instanceof IMessage) {
            this.sourceId = ((IMessage) source).getSourceId();
        }
        this.messageType = messageType;
    }

    public MessageApplicationEvent bindEvent(Object event) {
        this.event = event;
        return this;
    }

    public MessageApplicationEvent bindSource(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getMessageType() {
        return messageType;
    }

    public Object getEvent() {
        return this.event;
    }

}
