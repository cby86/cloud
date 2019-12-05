package com.spring.cloud.config;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class CustomerPathPatternServerWebExchangeMatcher implements ServerWebExchangeMatcher {
    private static final PathPatternParser DEFAULT_PATTERN_PARSER = new PathPatternParser();

    private final PathPattern pattern;
    private final HttpMethod method;

    public CustomerPathPatternServerWebExchangeMatcher(String pattern, HttpMethod method) {
        Assert.notNull(pattern, "pattern cannot be null");
        this.pattern = DEFAULT_PATTERN_PARSER.parse(pattern);
        this.method = method;
    }

    public CustomerPathPatternServerWebExchangeMatcher(String pattern) {
        this(pattern, null);
    }

    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        if (this.method != null && !this.method.equals(request.getMethod())) {
            return ServerWebExchangeMatcher.MatchResult.notMatch();
        }
        PathContainer path = request.getPath().pathWithinApplication();
        boolean match = this.pattern.matches(path);
        if (!match) {
            return ServerWebExchangeMatcher.MatchResult.notMatch();
        }
        Map<String, String> pathVariables = this.pattern.matchAndExtract(path).getUriVariables();
        Map<String, Object> variables = new HashMap<>(pathVariables);
        return ServerWebExchangeMatcher.MatchResult.match(variables);
    }

    public boolean isSelf(String pattern) {
        return this.pattern != null && pattern.equals(this.pattern.getPatternString());
    }

    @Override
    public String toString() {
        return "CustomerPathPatternServerWebExchangeMatcher{" +
                "pattern='" + pattern + '\'' +
                ", method=" + method +
                '}';
    }

}
