package com.spring.cloud.service;

public interface MessageService {
    void doProcess(String eventId,Object message, String messageType);
}
