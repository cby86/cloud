package com.spring.cloud.service.impl.message.handler;

public interface MessageHandler  {

    void doProcess(int eventId,String sourceId, Object message);

    public String supportMessageType();
}
