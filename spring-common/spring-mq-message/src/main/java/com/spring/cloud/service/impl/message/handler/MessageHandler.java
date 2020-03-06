package com.spring.cloud.service.impl.message.handler;

public interface MessageHandler  {

    void doProcess(String eventId, Object message);

    public String supportMessageType();
}
