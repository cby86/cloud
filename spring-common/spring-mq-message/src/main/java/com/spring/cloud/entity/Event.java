package com.spring.cloud.entity;

import com.alibaba.fastjson.JSON;
import com.spring.cloud.base.IntIdBaseEntity;
import com.spring.cloud.service.EventService;
import com.spring.cloud.utils.JodaTimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mb_hunter_event")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Event extends IntIdBaseEntity {
    public transient final static int maxTimes = 3;
    /**
     * 发送超时时间，5分钟，超过这个时间没有发送状态变更的新消息会进行重发
     */
    public transient final static int timeout = 5;
    private String eventType;
    @Column(columnDefinition = "longtext")
    private String payload;
    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus=EventStatus.PRODUCER_NEW;
    private int retryCount;

    private boolean markerError;

    @Column(length = 1024)
    private String reason;

    private Date overdue;

    private String sourceId;

    private String fromEvent;


    public void increaseRetry() {
        this.retryCount++;
    }


    public static Event createEvent(EventStatus status, Object message, String messageType,String sourceId,String fromEventId) {
        Event event = new Event();
        event.setPayload(JSON.toJSONString(message));
        event.setEventStatus(status);
        event.setEventType(messageType);
        event.setSourceId(sourceId);
        event.setOverdue(JodaTimeUtils.plusMinutes(new Date(),Event.timeout));
        event.setFromEvent(fromEventId);
        return event;
    }
}
