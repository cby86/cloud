package com.spring.cloud.service;

import com.alibaba.fastjson.JSON;
import com.spring.cloud.entity.Event;
import com.spring.cloud.entity.EventStatus;
import com.spring.cloud.message.MessageApplicationEvent;
import com.spring.cloud.repository.EventRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

public abstract class BaseService implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Autowired
    private EventRepository repository;

    public void publishEvent(MessageApplicationEvent messageApplicationEvent) {
        Event event = new Event();
        event.setPayload(JSON.toJSONString(messageApplicationEvent.getSource()));
        event.setEventStatus(EventStatus.PRODUCER_NEW);
        event.setEventType(messageApplicationEvent.getMessageType());
        repository.save(event);
        applicationContext.publishEvent(messageApplicationEvent.bindEvent(event.getId()));
    }
    public void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
