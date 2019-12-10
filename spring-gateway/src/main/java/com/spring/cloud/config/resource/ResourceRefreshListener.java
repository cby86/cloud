package com.spring.cloud.config.resource;
import com.spring.cloud.config.CustomerReactiveAuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class ResourceRefreshListener implements ApplicationListener<ResourceRemoteApplicationEvent> {
    @Autowired
    private CustomerReactiveAuthorizationManager customerReactiveAuthorizationManager;
    @Autowired
    private ServiceMatcher serviceMatcher;
    @Override
    public void onApplicationEvent(ResourceRemoteApplicationEvent event) {
        if (serviceMatcher.isForSelf(event)) {
            customerReactiveAuthorizationManager.refresh();
        }
    }
}
