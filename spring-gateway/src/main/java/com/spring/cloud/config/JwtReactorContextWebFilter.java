package com.spring.cloud.config;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.spring.cloud.JwtHelper;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtReactorContextWebFilter implements WebFilter {

    ServerBearerTokenAuthenticationConverter serverBearerTokenAuthenticationConverter = new ServerBearerTokenAuthenticationConverter();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext().map(securityContext -> {
            Authentication authentication = securityContext.getAuthentication();
            ServerHttpRequest mutableReq = exchange.getRequest().mutate().header("Authorization", new String[]{JSONObject.toJSONString(authentication.getPrincipal())}).build();
            ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
            return mutableExchange;
        }).switchIfEmpty(Mono.just(exchange)).flatMap(e -> chain.filter(e)).subscriberContext(c -> c.hasKey(SecurityContext.class) ? c :
                withJwtSecurityContext(c, exchange)
        );
    }

    private Context withJwtSecurityContext(Context mainContext, ServerWebExchange exchange) {
        Mono<Authentication> convert = serverBearerTokenAuthenticationConverter.convert(exchange);
        return mainContext.putAll(convert.map((Authentication authentication) -> {
            Map<String, Claim> stringClaimMap = null;
            try {
                stringClaimMap = JwtHelper.verifyToken(authentication.getPrincipal().toString());
            } catch (Exception jwtSecurityContext) {
                throw new InsufficientAuthenticationException("JWT签名不合法,请重新授权");
            }
            Claim userName = stringClaimMap.get("user_name");
            Claim authorities = stringClaimMap.get("authorities");
            Claim identify = stringClaimMap.get("identify");
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", userName.asString());
            userInfo.put("id", identify.asString());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            if (authorities != null) {
                List<String> roles = authorities.asList(String.class);
                for (String role : roles) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(role));
                }
            }
            SecurityContextImpl securityContext = new SecurityContextImpl();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userInfo, identify.asString(), grantedAuthorities);
            securityContext.setAuthentication(usernamePasswordAuthenticationToken);
            return securityContext;
        }).as(ReactiveSecurityContextHolder::withSecurityContext));
    }
}
