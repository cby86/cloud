package com.spring.cloud.controller;

import com.spring.cloud.FilterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

@RestController
@RequestMapping("")
@RefreshScope
public class FallbackController {
    Logger logger = LoggerFactory.getLogger(FallbackController.class);
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
