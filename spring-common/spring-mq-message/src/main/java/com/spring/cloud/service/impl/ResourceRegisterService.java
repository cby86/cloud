package com.spring.cloud.service.impl;

import com.spring.cloud.global.ResourceDefine;
import com.spring.cloud.global.ResourceRegister;
import com.spring.cloud.message.MessageApplicationEvent;
import com.spring.cloud.message.MessageType;
import com.spring.cloud.service.EventBaseProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceRegisterService extends EventBaseProcessor implements ResourceRegister {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.port}")
    private String port;
    @Override
    public void registerEndpoint(List<ResourceDefine> resource) {
        this.publishMqEvent(new MessageApplicationEvent(resource, MessageType.ResourceRegister.getRouterKey()).bindSource(appName+":"+port));
    }
}
