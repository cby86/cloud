package com.spring.cloud.utils;
import org.springframework.security.Role;
import org.springframework.security.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.Collection;

public class SecurityUtils {
    public static SecurityUser currentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (authentication == null) {
                return null;
            }
            Object principal = authentication.getPrincipal();
            Collection<Role> authorities = (Collection<Role>) authentication.getAuthorities();
            SecurityUser user = new SecurityUser();
            if (!StringUtils.isEmpty(principal)) {
                user.setUsername(principal.toString());
            }else {
                return null;
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
