package com.spring.cloud.support.listener;

import org.springframework.context.ApplicationEvent;

public class RoleChangeEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public RoleChangeEvent(Object source) {
        super(source);
    }
}
