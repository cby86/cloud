package com.spring.cloud.config;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * 定时获取资源权限，并更新
 */
public class CustomerReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private ResourceLoader resourceLoader;
    private List<ResourceMatcher> mappings = new ArrayList<>();
    private boolean allowAnyOtherResource = true;
    private ScheduledExecutorService scheduler;
    @Value("${resource.loader.delay:5}")
    private int resourceLoaderDelay = 5;
    @Value("${resource.refresh.period:10}")
    private int resourceRefreshPeriod = 10;
    /**
     * 匹配网关配置的前缀,可能是多级目录
     */
    private final static String urlPrefixMarcher = "/**";

    public CustomerReactiveAuthorizationManager(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        scheduler = Executors.newScheduledThreadPool(1,
                new ThreadFactoryBuilder()
                        .setNameFormat("ResourceAuthSync-%d")
                        .setDaemon(true)
                        .build());
    }

    public void setAllowAnyOtherResource(boolean allowAnyOtherResource) {
        this.allowAnyOtherResource = allowAnyOtherResource;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono authentication, AuthorizationContext context) {
        return Flux.fromIterable(mappings)
                .concatMap(mapping -> mapping.getMatcher().matches(context.getExchange())
                        .filter(ServerWebExchangeMatcher.MatchResult::isMatch)
                        .flatMap(matchResult -> mapping.getReactiveAuthorizationManager().check(authentication, context))
                ).next().switchIfEmpty(Mono.just(new AuthorizationDecision(allowAnyOtherResource)));
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
                    Map<String, List<String>> resource = resourceLoader.loadResource();
                    List<ResourceMatcher> newResourceMatcherList = new ArrayList<>();
                    for (Map.Entry<String, List<String>> entry : resource.entrySet()) {
                        ResourceMatcher matcher = mergeResourceMatcher(entry.getKey(), entry.getValue());
                        /**
                         * 新增或修改过的matcher
                         */
                        if (matcher != null) {
                            newResourceMatcherList.add(matcher);
                        }
                    }

                    mappings = newResourceMatcherList;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, resourceLoaderDelay, resourceRefreshPeriod, TimeUnit.SECONDS);
    }

    private ResourceMatcher mergeResourceMatcher(String key, List<String> authorities) {
        if (authorities.isEmpty()) {
            return null;
        }
        /**
         * 将path中的｛｝，替换为通配符
         */
        key = key.replaceAll("\\{[^}]*\\}", "*");
        return new ResourceMatcher(urlPrefixMarcher+key, authorities);
    }

    @Override
    public String toString() {

        Map<String, List<String>> result = new HashMap<>();
        for (ResourceMatcher matcher:mappings) {
            result.put(matcher.getMatcher().getPattern().getPatternString(), matcher.getReactiveAuthorizationManager().getAuthorities());
        }
        return JSON.toJSONString(result);
    }
}
