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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Configuration
public class FilterConfig {


    /**
     * 根据IP地址来限制流量
     *
     * @return
     */
    @Bean
    KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    @Order(-1)
    public GlobalFilter a() {
        return (exchange, chain) -> ReactiveSecurityContextHolder.getContext().map(c -> {
            Authentication authentication = c.getAuthentication();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", authentication.getPrincipal());
            userInfo.put("authorities", authentication.getAuthorities());
            userInfo.put("identify", authentication.getCredentials());
            ServerHttpRequest mutableReq = exchange.getRequest().mutate().header("user",JSONObject.toJSONString(userInfo)).build();
            ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
            return mutableExchange;
        }).flatMap(e->chain.filter(e).then(Mono.fromRunnable(() -> {
            System.err.println("first post filter");
        })));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        Flux.merge(Flux.range(0,5),Flux.range(2,5)).subscribe(System.out::println);
//        Flux.range(1, 10).reduceWith(() -> 10, (x, y) -> x + y).subscribe(System.out::println);
//        List<String> df = Flux.just("df").toStream().collect(Collectors.toList());
//        List<Object> t = Flux.empty().concatWith(Mono.just("t")).toStream().collect(Collectors.toList());
//        t.stream().forEach(System.out::println);
//        Mono.empty().concatWith(Mono.just("t")).then();
        Mono.just("t").map(t -> t + "tt").flatMap(t -> {
            System.out.println(t);
            return Mono.empty().then(Mono.fromRunnable(() -> {
                System.err.println("first post filter");
            }));
        }).subscribe();
    }


}
