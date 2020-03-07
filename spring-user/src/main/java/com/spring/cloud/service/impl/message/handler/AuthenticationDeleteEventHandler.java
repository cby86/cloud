package com.spring.cloud.service.impl.message.handler;

import com.spring.cloud.message.MessageType;
import com.spring.cloud.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationDeleteEventHandler extends AbstractMessageHandler {
    @Autowired
    private AuthenticationService authenticationService;
    @Override
    public void onMessage(String eventId, Object message) {
        authenticationService.deleteAllAuthentication(message.toString());
    }

    @Override
    public String supportMessageType() {
        return MessageType.MenuDelete.getRouterKey();
    }
}
