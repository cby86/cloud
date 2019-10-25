package com.spring.cloud.utils;
import org.springframework.security.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.Collection;

public class SecurityUtils {
    public static User currentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (authentication == null) {
                return null;
            }
            Object principal = authentication.getPrincipal();
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
            User user = new User();
            if (!StringUtils.isEmpty(principal)) {
                user.setUsername(principal.toString());
            }
            if (authorities != null) {
                user.setAuthorities(authorities);
            }
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
