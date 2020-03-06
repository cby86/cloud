package com.spring.cloud.service.impl.message.handler;


import com.alibaba.fastjson.JSON;
import com.spring.cloud.message.MessageType;
import com.spring.cloud.service.AuthenticationService;
import com.spring.cloud.service.dto.MenuMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationChangeEventHandler extends AbstractMessageHandler {
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void onMessage(String eventId, Object message) {
        MenuMessage menuMessage = JSON.parseObject(message.toString(), MenuMessage.class);
        authenticationService.updateAuthentications(menuMessage);
    }

    @Override
    public String supportMessageType() {
        return MessageType.MenuChange.name();
    }
}
