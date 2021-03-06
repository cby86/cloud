package com.spring.cloud.service.impl.message.handler;


import com.fasterxml.jackson.core.type.TypeReference;
import com.spring.cloud.message.MessageType;
import com.spring.cloud.service.AuthenticationService;
import com.spring.cloud.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceAddEventHandler extends AbstractMessageHandler {
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void onMessage(String menuId,Object message) {
        List<String> urls= JsonUtils.jsonToObjectList(message.toString(), new TypeReference<List<String>>() {});
        authenticationService.addAuthenticationDetails(menuId,urls);
    }

    @Override
    public String supportMessageType() {
        return MessageType.BindResource.getRouterKey();
    }
}
