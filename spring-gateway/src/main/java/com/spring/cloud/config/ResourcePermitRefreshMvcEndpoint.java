package com.spring.cloud.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

@Endpoint(id="resourcePermitInfo")
public class ResourcePermitRefreshMvcEndpoint  {
    @Autowired
    CustomerReactiveAuthorizationManager customerReactiveAuthorizationManager;
    @ReadOperation
    public String resourcePermitInfo() {
        return customerReactiveAuthorizationManager.toString();
    }
}
