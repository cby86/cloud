package com.spring.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
@RefreshScope
public class FallbackController {
    @Value("${route.returnFailRoutePath}")
    private boolean returnFailRoutePath = false;

    @RequestMapping("/fallback")
    public Map<String, Object> fallback(ServerWebExchange exchange, BindingContext bindingContext) {
        LinkedHashSet<String> attribute = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        Map<String, Object> data = new HashMap<>();
        data.put("status", 0);
        data.put("message", "系统繁忙，请稍后再试");
        if (returnFailRoutePath) {
            data.put("data", attribute.toString());
        }
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        return data;
    }

}
