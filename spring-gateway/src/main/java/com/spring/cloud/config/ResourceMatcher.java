package com.spring.cloud.config;
import io.jsonwebtoken.lang.Assert;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

import java.util.List;
import java.util.Objects;

public class ResourceMatcher  {
    private CustomerPathPatternServerWebExchangeMatcher matcher;
    private CustomerAuthorizationManager reactiveAuthorizationManager;


    public ResourceMatcher(String key, List<String> value) {
        Assert.notNull(key);
        Assert.notEmpty(value);
        this.matcher = new CustomerPathPatternServerWebExchangeMatcher(key);
        this.reactiveAuthorizationManager = new CustomerAuthorizationManager(value);
    }


    public CustomerPathPatternServerWebExchangeMatcher getMatcher() {
        return matcher;
    }

    public CustomerAuthorizationManager getReactiveAuthorizationManager() {
        return reactiveAuthorizationManager;
    }
}
