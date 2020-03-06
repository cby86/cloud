package com.spring.cloud.service.impl.message.handler;

import com.spring.cloud.service.EventBaseProcessor;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractMessageHandler  extends EventBaseProcessor implements MessageHandler {
    @Override
    @Transactional
    public void doProcess(String eventId, Object message) {
        this.onMessage(eventId,message);
        this.commitMqEvent(eventId);
    }

    protected abstract void onMessage(String eventId, Object message);
}
