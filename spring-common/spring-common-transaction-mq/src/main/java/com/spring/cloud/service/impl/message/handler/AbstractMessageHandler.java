package com.spring.cloud.service.impl.message.handler;

import com.spring.cloud.service.EventBaseProcessor;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractMessageHandler  extends EventBaseProcessor implements MessageHandler {
    @Override
    @Transactional
    public void doProcess(int eventId,String sourceId,Object message) {
        this.onMessage(sourceId,message);
        this.commitMqEvent(eventId);
    }

    protected abstract void onMessage(String sourceId,Object message);
}
