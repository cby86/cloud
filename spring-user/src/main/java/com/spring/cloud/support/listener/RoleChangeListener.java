package com.spring.cloud.support.listener;

import com.spring.cloud.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RoleChangeListener implements ApplicationListener<RoleChangeEvent> {
    @Autowired
    private AuthenticationService authenticationService;
    @Override
    public void onApplicationEvent(RoleChangeEvent event) {
        authenticationService.clearAuthentication();
    }
}
