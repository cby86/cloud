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
import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;


    @Override
    public void errorToSendEventMessage(Object eventId,String reason) {
        eventRepository.updateEvent(eventId, EventStatus.PRODUCER_ERROR,reason);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Event> loadEventByStatus(EventStatus eventStatus, int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        pageable.getSort().and(Sort.by(Sort.Order.asc("id")));
        return eventRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            predicates.add(criteriaBuilder.equal(root.get("eventStatus"), eventStatus));
            predicates.add(criteriaBuilder.equal(root.get("markerError"), true));
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
        eventRepository.updateEvent(eventId, EventStatus.PROCESSORED);
    }
}
