package com.spring.cloud.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时获取资源权限，并更新
 */
public class CustomerReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ResourceLoader resourceLoader;
    private ResourceMatcher mappings;
    private ScheduledExecutorService scheduler;
    @Value("${resource.loader.delay:5}")
    private int resourceLoaderDelay = 5;
    /**
     * 设置定时器执行周期,定时刷新权限配置，默认为秒
     */
    @Value("${resource.refresh.period:1800}")
    private int resourceRefreshPeriod = 1800;

    public CustomerReactiveAuthorizationManager(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        scheduler = Executors.newScheduledThreadPool(1,
                new ThreadFactoryBuilder()
                        .setNameFormat("ResourceAuthSync-%d")
                        .setDaemon(true)
                        .build());
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        return Flux.just(mappings)
                .concatMap(mapping -> mapping.matches(context.getExchange())
                        .filter(ServerWebExchangeMatcher.MatchResult::isMatch)
                        .map(r -> r.getVariables())
                        .flatMap(variables -> mapping.check(authentication, new AuthorizationContext(context.getExchange(),variables))
                        )
                )
                .next()
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 启动定时器，根据设定的周期向资源中心加载最新资源权限
     */
    @PostConstruct
    public void init() {
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    if (resourceLoader == null) {
                        return;
                    }
                    refresh();
                } catch (Exception ex) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("获取权限配置信息错误", ex.getMessage());
                    }
                }
            }
        }, resourceLoaderDelay, resourceRefreshPeriod, TimeUnit.SECONDS);
    }

    @Override
    public String toString() {
        return mappings.toString();
    }

    public void refresh() {
        Map<String, Set<String>> resource = resourceLoader.loadResource();
        if (mappings == null) {
            mappings = new ResourceMatcher(resource);
        } else {
            mappings.reInit(resource);
        }
    }
}
