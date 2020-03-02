package com.spring.cloud.entity;

import com.spring.cloud.base.BaseEntity;
import com.spring.cloud.base.IntIdBaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "mb_hunter_event")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Event extends IntIdBaseEntity {
    public transient final static int maxTimes = 3;
    private String eventType;
    @Column(columnDefinition = "longtext")
    private String payload;
    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus=EventStatus.PRODUCER_NEW;
    private int retryCount;

    private boolean markerError;

    private String reason;

    public void increaseRetry() {
        this.retryCount++;
    }
}
