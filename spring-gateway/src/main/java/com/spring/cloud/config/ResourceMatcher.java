package com.spring.cloud.config;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

import java.util.List;

public class ResourceMatcher  {
    private PathPatternParserServerWebExchangeMatcher matcher;
    private ReactiveAuthorizationManager reactiveAuthorizationManager;


    public ResourceMatcher(String key, List<String> value) {
        Assert.notNull(key);
        Assert.notEmpty(value);
        this.matcher = new PathPatternParserServerWebExchangeMatcher(key);
        this.reactiveAuthorizationManager = new CustomerAuthorizationManager(value);
    }


    public PathPatternParserServerWebExchangeMatcher getMatcher() {
        return matcher;
    }

    public ReactiveAuthorizationManager getReactiveAuthorizationManager() {
        return reactiveAuthorizationManager;
    }
}
