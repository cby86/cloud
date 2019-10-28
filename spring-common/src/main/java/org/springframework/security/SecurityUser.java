package org.springframework.security;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser implements UserDetails {
    private String username;
    private String password;
    private Collection<CustomerSimpleGrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(Collection<CustomerSimpleGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addAuthority(String role) {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        authorities.add(new CustomerSimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<CustomerSimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }
}
