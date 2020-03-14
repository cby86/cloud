package com.spring.cloud.service;

public interface MessageService {
    void doProcess(int eventId,String sourceId,Object message, String messageType);
}
