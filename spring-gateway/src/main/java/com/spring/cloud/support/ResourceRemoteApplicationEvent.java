package com.spring.cloud.support;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class ResourceRemoteApplicationEvent extends RemoteApplicationEvent {

    @SuppressWarnings("unused")
    public ResourceRemoteApplicationEvent() {
        // for serializers
    }

    public ResourceRemoteApplicationEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }
}
