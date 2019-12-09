package com.spring.cloud.endpoint;
import com.spring.cloud.bus.ResourceRemoteApplicationEvent;
import com.spring.cloud.config.CustomerReactiveAuthorizationManager;
import javafx.beans.NamedArg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationEventPublisher;

@Endpoint(id="resource-permit")
public class ResourcePermitRefreshMvcEndpoint {

    private ApplicationEventPublisher context;

    private String appId;


    public ResourcePermitRefreshMvcEndpoint(ApplicationEventPublisher context, String appId) {
        this.context = context;
        this.appId = appId;
    }

    @Autowired(required = false)
    CustomerReactiveAuthorizationManager customerReactiveAuthorizationManager;

    @ReadOperation
    public String resourcePermitInfo() {
        if (customerReactiveAuthorizationManager != null) {
            return customerReactiveAuthorizationManager.toString();
        }
        return null;
    }

    @WriteOperation
    public void refresh(@Selector String destination) {
        context.publishEvent(new ResourceRemoteApplicationEvent(this,appId,destination));
    }
}
