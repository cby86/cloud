package com.spring.cloud.service;

import com.spring.cloud.entity.Event;
import com.spring.cloud.entity.EventStatus;
import com.spring.cloud.message.MessageApplicationEvent;
import com.spring.cloud.repository.EventRepository;
import org.omg.CORBA.UNKNOWN;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.transaction.annotation.Transactional;

public abstract class EventBaseProcessor implements ApplicationContextAware {
    protected ApplicationContext applicationContext;
    @Autowired
    private EventRepository repository;

    public void publishMqEvent(MessageApplicationEvent messageApplicationEvent) {
        Event event = Event.createEvent(EventStatus.PRODUCER_NEW, messageApplicationEvent.getSource(),
                messageApplicationEvent.getMessageType(),messageApplicationEvent.getSourceId());
        repository.save(event);
        applicationContext.publishEvent(messageApplicationEvent.bindEvent(event.getId()));
    }
    public void commitMqEvent (String eventId) {
        Event event = repository.findEventBySource(eventId);
        if (event != null) {
            event.setEventStatus(EventStatus.CONSUMER_PROCESSORED);
        }
        repository.save(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
