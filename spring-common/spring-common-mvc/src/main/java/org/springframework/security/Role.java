package org.springframework.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author
 * @Date 2019/11/7/007 11:10
 **/
@Getter
@Setter
public class Role implements GrantedAuthority {
    private String authority;
}
