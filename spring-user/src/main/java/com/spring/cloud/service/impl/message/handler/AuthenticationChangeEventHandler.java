package com.spring.cloud.service.impl.message.handler;


import com.spring.cloud.message.MessageType;
import com.spring.cloud.service.AuthenticationService;
import com.spring.cloud.service.dto.MenuMessage;
import com.spring.cloud.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationChangeEventHandler extends AbstractMessageHandler {
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void onMessage(String eventId, Object message) {
        MenuMessage menuMessage = JsonUtils.jsonToObject(message.toString(), MenuMessage.class);
        authenticationService.updateAuthentications(menuMessage);
    }

    @Override
    public String supportMessageType() {
        return MessageType.MenuChange.getRouterKey();
    }
}
