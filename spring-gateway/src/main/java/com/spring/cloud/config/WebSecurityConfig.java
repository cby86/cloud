package com.spring.cloud.config;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 支持动态和静态权限
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {


    @Bean
    @ConditionalOnBean(ResourceLoader.class)
    public CustomerReactiveAuthorizationManager getCustomerReactiveAuthorizationManager(ResourceLoader resourceLoader) {
        return new CustomerReactiveAuthorizationManager(resourceLoader);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, @Autowired(required = false) CustomerReactiveAuthorizationManager customerReactiveAuthorizationManager) {
        http.csrf().disable();
        http.httpBasic().disable();
        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec = http.authorizeExchange();
        //配置URL静态权限
        authorizeExchangeSpec.pathMatchers("/backend/order/home").hasAuthority("ROLE_ADMIN"); //无需进行权限过滤的请求路径
        //配置URL动态权限
        if (customerReactiveAuthorizationManager != null) {
            authorizeExchangeSpec.anyExchange().access(customerReactiveAuthorizationManager);
        }
        http.exceptionHandling()
                .authenticationEntryPoint(getServerAuthenticationEntryPoint())
                .accessDeniedHandler(getAccessDeniedHandler())
//                .and().oauth2ResourceServer().jwt().jwtDecoder(new NimbusJwtDecoderJwkSupport())
//                .and().bearerTokenConverter(new ServerBearerTokenAuthenticationConverter())
                .and().addFilterAt(new JwtReactorContextWebFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT);

        return http.build();
    }

    private ServerAccessDeniedHandler getAccessDeniedHandler() {
        return (exchange, exception) -> exceptionHandler(exchange);
    }

    private Mono<Void> exceptionHandler(ServerWebExchange exchange) {
        org.springframework.http.server.reactive.ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        Map<String, Object> data = new HashMap<>();
        data.put("status", 0);
        data.put("message", "没有权限访问");
        byte[] response = new JSONObject(data).toJSONString()
                .getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        originalResponse.writeWith(Flux.just(buffer));
        return originalResponse.writeWith(Flux.just(buffer));
    }

    private ServerAuthenticationEntryPoint getServerAuthenticationEntryPoint() {
        return (exchange, exception) -> exceptionHandler(exchange);
    }
}
