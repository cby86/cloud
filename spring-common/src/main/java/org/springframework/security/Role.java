package org.springframework.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Author panyuanjun
 * @Date 2019/11/7/007 11:10
 **/
public class Role implements GrantedAuthority {
    private String id;

    private String name;

    private String code;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getAuthority() {
        return code;
    }
}
