package com.spring.cloud.config;

import com.alibaba.fastjson.JSON;
import com.spring.cloud.global.SystemDefine;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

public class ResourceMatcher implements ServerWebExchangeMatcher,ReactiveAuthorizationManager<AuthorizationContext>{
    /**
     * 前缀 所有路由到服务的URL 都有固定前缀
     */
    private final static String urlPrefixMarcher = "/**";
    private final static String MAPPING_ROLE_NAME = "mapping_role_name";
    private Map<String, Set<String>> mappings;

    public ResourceMatcher(Map<String, Set<String>> mappings) {
        Assert.notNull(mappings);
        this.reInit(mappings);
    }

    @Override
    public Mono<MatchResult> matches(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        PathContainer path = request.getPath().pathWithinApplication().subPath(2);
        String value = path.value();
        Set<String> roles = mappings.get(value);
        HashMap<String, Object> variables = new HashMap<>();
        if (roles != null) {
            variables.put(MAPPING_ROLE_NAME, roles);
            return MatchResult.match(variables);
        }
        for (Map.Entry<String, Set<String>> entry : mappings.entrySet()) {
            if (new PathPatternParser().parse(urlPrefixMarcher + entry.getKey()).matches(path)) {
                variables.put(MAPPING_ROLE_NAME, roles);
                roles = entry.getValue();
                break;
            }
        }
        return roles == null ? MatchResult.notMatch() : MatchResult.match(variables);
    }

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        Set<String> authorities = (Set<String>) context.getVariables().get(MAPPING_ROLE_NAME);
        if (CollectionUtils.isEmpty(authorities)) {
            return Mono.just(new AuthorizationDecision(true));
        }
        return authentication
                .filter(a -> a.isAuthenticated())
                .flatMapIterable(a -> a.getAuthorities())
                .map(g -> g.getAuthority())
                .filter(s -> s.equals(SystemDefine.SUPER_ADMIN) || authorities.contains(s))
                .hasElements()
                .map(hasAuthority -> new AuthorizationDecision(hasAuthority))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    @Override
    public String toString() {
        return JSON.toJSONString(mappings);
    }

    public void reInit(Map<String, Set<String>> mappings) {
        this.mappings = mappings;
    }

}
