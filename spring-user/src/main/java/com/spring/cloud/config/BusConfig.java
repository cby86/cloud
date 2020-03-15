package com.spring.cloud.config;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@RemoteApplicationEventScan(basePackages = "com.spring.cloud.support")
public class BusConfig {

}
