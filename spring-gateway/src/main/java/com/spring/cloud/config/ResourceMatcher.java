package com.spring.cloud.config;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.*;

public class ResourceMatcher  {
    private final static String SUPER_ADMIN = "ROLE_ADMIN";
    private Map<String, Set<String>> mappings;
    private List<PathPattern> pathPatterns;

    public ResourceMatcher(Map<String, Set<String>> mappings) {
        Assert.notNull(mappings);
        this.reInit(mappings);
    }

    public Set<String> matches(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        PathContainer path = request.getPath().pathWithinApplication();
        Set<String> roles= null;
        boolean match = false;
        for (PathPattern pathPattern : pathPatterns) {
            if (pathPattern.matches(path)) {
                String patternString = pathPattern.getPatternString();
                roles = mappings.get(patternString);
                match = true;
                break;
            }
        }
        return roles == null ? new HashSet<>() : roles;
    }

    public Mono<AuthorizationDecision> check(ServerWebExchange exchange,Mono<Authentication> authentication, AuthorizationContext object) {
        Set<String> authorities = this.matches(exchange);
        return authentication
                .filter(a -> a.isAuthenticated())
                .flatMapIterable(a -> a.getAuthorities())
                .map(g -> g.getAuthority())
                .filter(s -> s.equals(SUPER_ADMIN) || authorities.contains(s))
                .hasElements()
                .map(hasAuthority -> new AuthorizationDecision(hasAuthority))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    @Override
    public String toString() {
        return JSON.toJSONString(mappings);
    }

    public void reInit(Map<String, Set<String>> mappings) {
        this.pathPatterns = new ArrayList<>();
        this.mappings = mappings;
        mappings.forEach((url, roles)->this.pathPatterns.add(new PathPatternParser().parse(url)));
    }
}
