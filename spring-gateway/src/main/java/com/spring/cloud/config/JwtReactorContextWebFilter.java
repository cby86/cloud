package com.spring.cloud.config;
import com.auth0.jwt.interfaces.Claim;
import com.spring.cloud.JwtHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .subscriberContext(c -> c.hasKey(SecurityContext.class) ? c :
                        withJwtSecurityContext(c, exchange)
                );
    }

    private Context withJwtSecurityContext(Context mainContext, ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        SecurityContext securityContext = null;
        if (!StringUtils.isEmpty(token)) {
            if (token.indexOf("Bearer ") > -1) {
                token = token.replace("Bearer ", "");
            }
            Map<String, Claim> stringClaimMap = JwtHelper.verifyToken(token);
            Claim userName = stringClaimMap.get("user_name");
            Claim authorities = stringClaimMap.get("authorities");
            Claim identify = stringClaimMap.get("identify");
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", userName.asString());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            if (authorities!=null) {
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
