package com.spring.cloud.repository;
import com.spring.cloud.base.BaseRepository;
import com.spring.cloud.entity.Event;
import com.spring.cloud.entity.EventStatus;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Date;

public interface EventRepository extends BaseRepository<Event, String> {

    @Modifying
    @Query("update Event obj set obj.eventStatus=?2,obj.reason=?3,obj.markerError=true where obj.id=?1")
    void updateEvent(Object eventId, EventStatus producerError,String reason);

    @Modifying
    @Query("update Event obj set obj.eventStatus=?2 where obj.id=?1 and obj.markerError=false")
    void updateEvent(Object eventId, EventStatus producerError);

    @Modifying
    @Query("delete from Event obj  where obj.eventStatus=?1 and obj.overdue<?2")
    void clearSuccessEvent( EventStatus producerError,Date now);
}
