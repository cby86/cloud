package com.spring.cloud.service.impl.handler;


import com.fasterxml.jackson.core.type.TypeReference;
import com.spring.cloud.global.ResourceDefine;
import com.spring.cloud.message.MessageType;
import com.spring.cloud.service.ResourceService;
import com.spring.cloud.service.impl.message.handler.AbstractMessageHandler;
import com.spring.cloud.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceRegisterEventHandler extends AbstractMessageHandler {
    @Autowired
    private ResourceService resourceService;

    @Override
    public void onMessage(String eventId, Object message) {
        List<ResourceDefine> resource = JsonUtils.jsonToObjectList(message.toString(), new TypeReference<List<ResourceDefine>>() {});
        resourceService.registerEndpoint(resource);
    }

    @Override
    public String supportMessageType() {
        return MessageType.ResourceRegister.getRouterKey();
    }
}
