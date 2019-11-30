package com.spring.cloud;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Configuration
public class FilterConfig {


    /**
     * 根据IP地址来限制流量
     *
     * @return
     */
    @Bean
    KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    @Order(-1)
    public GlobalFilter a() {
        return (exchange, chain) -> {
            System.err.println("前置处理");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.err.println("后置处理");
            }));
        };
    }
}
