package com.spring.cloud.config;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

@Endpoint(id="resourcePermitInfo")
public class ResourcePermitRefreshMvcEndpoint  {
    CustomerReactiveAuthorizationManager customerReactiveAuthorizationManager;

    public ResourcePermitRefreshMvcEndpoint(CustomerReactiveAuthorizationManager customerReactiveAuthorizationManager) {
        this.customerReactiveAuthorizationManager = customerReactiveAuthorizationManager;
    }

    @ReadOperation
    public String resourcePermitInfo() {
        return customerReactiveAuthorizationManager.toString();
    }
}
