package com.spring.cloud.repository;
import com.spring.cloud.base.BaseRepository;
import com.spring.cloud.entity.Event;
import com.spring.cloud.entity.EventStatus;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Date;

public interface EventRepository extends BaseRepository<Event, Integer> {


    @Modifying
    @Query("delete from Event obj  where obj.eventStatus=?1 and obj.overdue<?2")
    void clearSuccessEvent( EventStatus producerError,Date now);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select obj from Event obj  where obj.id=?1 and obj.deleted=false")
    Event findEventForUpdate(Object eventId);
    @Query("select obj from Event obj  where obj.fromEvent=?1 and obj.deleted=false")
    Event findByFromEvent(String eventId);

    @Modifying
    @Query("update  Event obj set obj.retryCount=obj.retryCount+1,obj.reason=?2 where obj.id=?1")
    void retryUpdate(int eventId, String reason);
}
