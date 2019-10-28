package org.springframework.security;
import org.springframework.security.core.GrantedAuthority;

public class CustomerSimpleGrantedAuthority implements GrantedAuthority {
    private String authority;

    public CustomerSimpleGrantedAuthority() {
    }

    public CustomerSimpleGrantedAuthority(String role) {
        this.authority = role;
    }


    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
