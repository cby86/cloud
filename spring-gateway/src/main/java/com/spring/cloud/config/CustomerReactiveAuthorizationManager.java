package com.spring.cloud.config;
import org.springframework.security.authorization.AuthorityReactiveAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private ResourceLoader resourceLoader;
    private List<ResourceMatcher> mappings;
    private boolean allowAnyOtherResource=true;

    public CustomerReactiveAuthorizationManager(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void setAllowAnyOtherResource(boolean allowAnyOtherResource) {
        this.allowAnyOtherResource = allowAnyOtherResource;
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono authentication, AuthorizationContext context) {
        return Flux.fromIterable(mappings)
                .concatMap(mapping -> mapping.getMatcher().matches(context.getExchange())
                        .filter(ServerWebExchangeMatcher.MatchResult::isMatch)
                        .flatMap(matchResult -> mapping.getReactiveAuthorizationManager().check(authentication,context))
                        //如果没有匹配的资源，根据配置是否通过验证
                        .switchIfEmpty(Mono.just(new AuthorizationDecision(allowAnyOtherResource)))
                ).next()
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    @PostConstruct
    public void init() {
        if (resourceLoader == null) {
            return;
        }
        Map<String, List<String>> resource = resourceLoader.loadResource();
        for (Map.Entry<String, List<String>> entry:resource.entrySet()){
            addResourceMatcher(entry.getKey(), entry.getValue());
        }
    }

    private void addResourceMatcher(String key, List<String> value) {
        if (mappings == null) {
            mappings = new ArrayList<>();
        }
        mappings.add(new ResourceMatcher(key,value));
    }
}
