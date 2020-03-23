package com.spring.cloud.support;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class AuthenticationChangeRemoteApplicationEvent extends RemoteApplicationEvent {
    @SuppressWarnings("unused")
    public AuthenticationChangeRemoteApplicationEvent() {
        // for serializers
    }
}
