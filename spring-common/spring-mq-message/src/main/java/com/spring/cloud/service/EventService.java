package com.spring.cloud.service;

import com.spring.cloud.entity.Event;
import com.spring.cloud.entity.EventStatus;
import org.springframework.data.domain.Page;


public interface EventService {
    void errorToSendEventMessage(Object eventId,String reason);

    Page<Event> loadProducerEvent(int page, int size);

    void save(Event event);

    void successToSendEvent(Object eventId);

    void clearSendMessage();

    boolean allow(String eventId, Object message, String messsageType);

    void errorToConsumerEventMessage(String eventId, String message);

    Page<Event> loadConsumerEvents(int page, int size);

    void clearConsumerMessage();

    void retryUpdate(int eventId, String reason);
}
