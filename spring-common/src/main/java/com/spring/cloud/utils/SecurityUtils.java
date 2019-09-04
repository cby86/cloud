package com.spring.cloud.utils;
import com.spring.cloud.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static User currentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (authentication == null) {
                return null;
            }
            Object principal = authentication.getPrincipal();

            return (User) principal;
        } catch (Exception e) {
            return null;
        }
    }
}
