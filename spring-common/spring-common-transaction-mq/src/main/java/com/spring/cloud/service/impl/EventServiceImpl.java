package com.spring.cloud.service.impl;

import com.spring.cloud.entity.Event;
import com.spring.cloud.entity.EventStatus;
import com.spring.cloud.repository.EventRepository;
import com.spring.cloud.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Page<Event> loadProducerEvent(int page,int size) {
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
        event.setEventStatus(EventStatus.PRODUCER_PROCESSORED);
        this.save(event);
    }

    @Override
    public void clearSendMessage() {
        eventRepository.clearSuccessEvent(EventStatus.PRODUCER_PROCESSORED,new Date());
    }

    @Override
    public Optional<Integer> check(String fromEvent, String sourceId, Object message, String messageType) {
        Event event = this.findByFromEvent(fromEvent);
        if (event == null) {
            event = Event.createEvent(EventStatus.CONSUMER_NEW, message, messageType,sourceId,fromEvent);
            eventRepository.save(event);
        }
        if (event.getEventStatus().equals(EventStatus.CONSUMER_NEW) || event.getEventStatus().equals(EventStatus.CONSUMER_ERROR)) {
            return Optional.of(event.getId());
        }
        return Optional.empty();
    }

    @Override
    public void errorToConsumerEventMessage(String eventId, String message) {
        Event event = this.findByFromEvent(eventId);
        if (event != null) {
            event.setEventStatus(EventStatus.CONSUMER_ERROR);
            event.setMarkerError(true);
            event.setReason(message);
            this.save(event);
        }
    }

    @Override
    public Page<Event> loadConsumerEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        pageable.getSort().and(Sort.by(Sort.Order.asc("id")));
        return eventRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            predicates.add(criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get("eventStatus"),
                    EventStatus.CONSUMER_ERROR),criteriaBuilder.equal(root.get("markerError"), true)),
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("eventStatus"), EventStatus.CONSUMER_NEW),
                            criteriaBuilder.lessThan(root.get("overdue"), new Date()))));
            predicates.add(criteriaBuilder.lt(root.get("retryCount"), Event.maxTimes));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }

    @Override
    public void clearConsumerMessage() {
        eventRepository.clearSuccessEvent(EventStatus.CONSUMER_PROCESSORED,new Date());
    }

    @Override
    public void retryUpdate(int eventId, String reason) {
        eventRepository.retryUpdate(eventId,reason);
    }

    @Override
    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    @Override
    public void clear() {
        eventRepository.deleteAll();
    }

    private Event findByFromEvent(String eventId) {
        return eventRepository.findByFromEvent(eventId);
    }
}
