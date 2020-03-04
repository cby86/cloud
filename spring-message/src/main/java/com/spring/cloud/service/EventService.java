package com.spring.cloud.service;

import com.spring.cloud.entity.Event;
import com.spring.cloud.entity.EventStatus;
import org.springframework.data.domain.Page;


public interface EventService {
    void errorToSendEventMessage(Object eventId,String reason);

    Page<Event> loadEventByStatus(int page, int size);

    void save(Event event);

    void successToSendEvent(Object eventId);

    void clearSendMessage();
}
