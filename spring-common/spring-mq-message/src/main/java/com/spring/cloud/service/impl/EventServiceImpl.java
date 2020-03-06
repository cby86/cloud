package com.spring.cloud.service.impl;

import ch.qos.logback.core.status.ErrorStatus;
import com.alibaba.fastjson.JSON;
import com.spring.cloud.entity.Event;
import com.spring.cloud.entity.EventStatus;
import com.spring.cloud.repository.EventRepository;
import com.spring.cloud.service.EventService;
import com.spring.cloud.utils.JodaTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;


    @Override
    public void errorToSendEventMessage(Object eventId,String reason) {
        Event event = eventRepository.findEventForUpdate(eventId);
        event.setEventStatus(EventStatus.PRODUCER_ERROR);
        event.setMarkerError(true);
        event.setReason(reason);
        this.save(event);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Event> loadEventByStatus(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        pageable.getSort().and(Sort.by(Sort.Order.asc("id")));
        return eventRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            predicates.add(criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get("eventStatus"),
                    EventStatus.PRODUCER_ERROR),criteriaBuilder.equal(root.get("markerError"), true)),
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("eventStatus"), EventStatus.PRODUCER_NEW),
                            criteriaBuilder.lessThan(root.get("overdue"), new Date()))));
            predicates.add(criteriaBuilder.lt(root.get("retryCount"), Event.maxTimes));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }

    @Override
    public void save(Event event) {
        eventRepository.save(event);
    }

    @Override
    public void successToSendEvent(Object eventId) {
        Event event = eventRepository.findEventForUpdate(eventId);
        if (event.isMarkerError()) {
            return;
        }
        event.setEventStatus(EventStatus.PROCESSORED);
        this.save(event);
    }

    @Override
    public void clearSendMessage() {
        eventRepository.clearSuccessEvent(EventStatus.PROCESSORED,new Date());
    }

    @Override
    public boolean allow(String eventId, Object message, String messageType) {
        Event event = this.findEventBySource(eventId);
        if (event == null) {
            event = Event.createEvent(EventStatus.CONSUMER_NEW, message, messageType,eventId);
            eventRepository.save(event);
            return true;
        }
        return event.getEventStatus().equals(EventStatus.CONSUMER_NEW) ||  event.getEventStatus().equals(EventStatus.CONSUMER_ERROR);
    }

    @Override
    public void errorToConsumerEventMessage(String eventId, String message) {
        Event event = this.findEventBySource(eventId);
        if (event != null) {
            event.setEventStatus(EventStatus.CONSUMER_ERROR);
            event.setMarkerError(true);
            event.setReason(message);
            this.save(event);
        }
    }

    private Event findEventBySource(String eventId) {
        return eventRepository.findEventBySource(eventId);
    }
}
