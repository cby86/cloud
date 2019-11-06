package com.spring.cloud;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FilterConfig {


    /**
     * 根据IP地址来限制流量
     * @return
     */
    @Bean
    KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    @Order(-1)
    public GlobalFilter a() {
        return (exchange, chain) -> {
            Map<String, Object> userInfo = new HashMap<>();
            Mono<SecurityContext> context = ReactiveSecurityContextHolder.getContext();
            context.subscribe(c -> {
                Authentication authentication = c.getAuthentication();
                userInfo.put("username", authentication.getPrincipal());
                userInfo.put("authorities", authentication.getAuthorities());
                userInfo.put("identify", authentication.getCredentials());
            });
            String user = JSONObject.toJSONString(userInfo);
            ServerHttpRequest mutableReq = exchange.getRequest().mutate().header("user", user).build();
            ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
            return chain.filter(mutableExchange).then(Mono.fromRunnable(() -> {
                System.err.println("first post filter");
            }));
        };
    }


}
