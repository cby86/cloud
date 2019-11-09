package com.spring.cloud.config;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.spring.cloud.JwtHelper;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
            ServerHttpRequest mutableReq = exchange.getRequest().mutate().header("Authorization", new String[]{JSONObject.toJSONString(authentication)}).build();
            ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
            return mutableExchange;
        }).switchIfEmpty(Mono.just(exchange)).flatMap(e -> chain.filter(e)).subscriberContext(c -> c.hasKey(SecurityContext.class) ? c :
                withJwtSecurityContext(c, exchange)
        );
    }

    private Context withJwtSecurityContext(Context mainContext, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        Mono<Authentication> convert = serverBearerTokenAuthenticationConverter.convert(exchange);
        SecurityContext securityContext = null;
        if (!StringUtils.isEmpty(token)) {
            if (token.indexOf("Bearer ") > -1) {
                token = token.replace("Bearer ", "");
            }
            Map<String, Claim> stringClaimMap = null;
            try {
                stringClaimMap = JwtHelper.verifyToken(token);
            } catch (Exception jwtSecurityContext) {
                return mainContext.putAll(Mono.justOrEmpty(securityContext)
                        .as(ReactiveSecurityContextHolder::withSecurityContext));
            }
            Claim userName = stringClaimMap.get("user_name");
            Claim authorities = stringClaimMap.get("authorities");
            Claim identify = stringClaimMap.get("identify");
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", userName.asString());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            if (authorities != null) {
                userInfo.put("authorities", authorities.asArray(String.class));
                List<String> roles = authorities.asList(String.class);
                for (String role : roles) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(role));
                }
            }
            securityContext = new SecurityContextImpl();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userInfo, identify.asString(), grantedAuthorities);
            securityContext.setAuthentication(authentication);
            return mainContext.putAll(Mono.just(securityContext)
                    .as(ReactiveSecurityContextHolder::withSecurityContext));
        }
        return mainContext.putAll(Mono.justOrEmpty(securityContext)
                .as(ReactiveSecurityContextHolder::withSecurityContext));
    }
}
