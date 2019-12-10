package com.spring.cloud.config.resource;
import com.spring.cloud.config.CustomerReactiveAuthorizationManager;
import com.spring.cloud.config.resource.endpoint.ResourcePermitRefreshMvcEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.SpringCloudBusClient;
import org.springframework.cloud.bus.event.AckRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@Configuration
@ConditionalOnClass(CustomerReactiveAuthorizationManager.class)
@RemoteApplicationEventScan(basePackageClasses = ResourceRemoteApplicationEvent.class)
public class ResourceEndpointConfig{
    @Bean
    @ConditionalOnEnabledEndpoint()
    public ResourcePermitRefreshMvcEndpoint getResourcePermitManager(ApplicationContext context, BusProperties busProperties) {
        return new ResourcePermitRefreshMvcEndpoint(context,busProperties.getId());
    }
}
