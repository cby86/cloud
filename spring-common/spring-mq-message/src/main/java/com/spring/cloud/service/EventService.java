package com.spring.cloud.service;

import com.spring.cloud.entity.Event;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


public interface EventService {
    void errorToSendEventMessage(Object eventId,String reason);

    Page<Event> loadProducerEvent(int page, int size);

    void save(Event event);

    void successToSendEvent(Object eventId);

    void clearSendMessage();

    Optional<Integer> check(String fromEventId, String sourceId, Object message, String messageType);

    void errorToConsumerEventMessage(String eventId, String message);

    Page<Event> loadConsumerEvents(int page, int size);

    void clearConsumerMessage();

    void retryUpdate(int eventId, String reason);

    List<Event> findAllEvent();

    void clear();
}
