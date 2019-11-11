package com.spring.cloud.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
    private ServerSecurityContextRepository serverSecurityContextRepository = new WebSessionServerSecurityContextRepository();

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();
        http.authorizeExchange()
                .pathMatchers("/backend/order/home").hasAuthority("ROLE_ADMIN") //无需进行权限过滤的请求路径
                .anyExchange().permitAll().and().exceptionHandling().authenticationEntryPoint(getServerAuthenticationEntryPoint())
//                .and().oauth2ResourceServer().jwt().jwtDecoder(new NimbusJwtDecoderJwkSupport())
//                .and().bearerTokenConverter(new ServerBearerTokenAuthenticationConverter())
                .and().httpBasic().disable().addFilterAt(new JwtReactorContextWebFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT);
        return http.build();
    }

    private ServerAuthenticationEntryPoint getServerAuthenticationEntryPoint() {
        return (exchange, exception) -> {
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
        };
    }
}
