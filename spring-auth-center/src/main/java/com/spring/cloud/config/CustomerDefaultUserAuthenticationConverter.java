package com.spring.cloud.config;

import org.springframework.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomerDefaultUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
    private static final String IDENTIFY = "identify";
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(IDENTIFY, ((SecurityUser) authentication.getPrincipal()).getId());
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }
}
