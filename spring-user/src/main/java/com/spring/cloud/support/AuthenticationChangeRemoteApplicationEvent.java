package com.spring.cloud.support;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class AuthenticationChangeRemoteApplicationEvent extends RemoteApplicationEvent {

    public AuthenticationChangeRemoteApplicationEvent(Object source, String originService) {
        super(source, originService);
    }
}
