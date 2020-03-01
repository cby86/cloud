package com.spring.cloud.entity;

import com.spring.cloud.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "mb_hunter_event")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Event extends BaseEntity {
    private String eventType;
    @Column(columnDefinition = "longtext")
    private String payload;
    @Enumerated(value = EnumType.STRING)
    private EventStatus eventStatus=EventStatus.PRODUCER_NEW;
}
