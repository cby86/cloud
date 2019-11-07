package org.springframework.security;

/**
 * @Author panyuanjun
 * @Date 2019/11/7/007 11:10
 **/
public class Role {
    private String name;

    private String code;

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
}
