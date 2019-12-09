package com.spring.cloud.bus;
import com.spring.cloud.config.CustomerReactiveAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ResourceRefreshListener implements ApplicationListener<ResourceRemoteApplicationEvent> {
    @Autowired
    private CustomerReactiveAuthorizationManager customerReactiveAuthorizationManager;
    @Override
    public void onApplicationEvent(ResourceRemoteApplicationEvent event) {
        customerReactiveAuthorizationManager.refresh();
    }
}
