package com.spring.cloud.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @RequestMapping("")
    public Map<String, Object> fallback(ServerWebExchange exchange,BindingContext bindingContext){
        Map<String, Object> data = new HashMap<>();
        data.put("status", 0);
        data.put("message", "系统繁忙，请稍后再试");
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        return data;
    }

}
