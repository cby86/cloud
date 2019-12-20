package com.spring.cloud;

import ch.qos.logback.core.rolling.helper.RenameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class FilterConfig {
    Logger logger = LoggerFactory.getLogger(FilterConfig.class);

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
            logger.info("前置处理");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("后置处理");
            }));
        };
    }
}
