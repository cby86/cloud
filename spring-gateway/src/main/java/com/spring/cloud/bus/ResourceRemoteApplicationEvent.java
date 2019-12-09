package com.spring.cloud.bus;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class ResourceRemoteApplicationEvent extends RemoteApplicationEvent {
    public ResourceRemoteApplicationEvent(Object source, String originService, String destinationService) {
        super(source, originService, destinationService);
    }
}
