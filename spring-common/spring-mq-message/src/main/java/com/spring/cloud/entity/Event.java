package com.spring.cloud.entity;

import com.spring.cloud.base.IntIdBaseEntity;
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

    private String reason;

    private Date overdue;


    public void increaseRetry() {
        this.retryCount++;
    }
}
