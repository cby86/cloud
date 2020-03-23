package com.spring.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "mb_hunter_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {
    private String username;

    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mb_hunter_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    private boolean system;

    public void addRoles(Role role) {
        if (CollectionUtils.isEmpty(roles)) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        getRoles().forEach(role -> list.add(new SimpleGrantedAuthority(role.getCode())));
        return list;
    }

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
}
