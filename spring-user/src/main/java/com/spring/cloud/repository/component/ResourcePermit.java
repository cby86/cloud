package com.spring.cloud.repository.component;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class ResourcePermit implements Serializable {
    private String url;
    private String role;
    /**
     * 前缀
     */
    private final static String urlPrefixMarcher = "/**";

    public ResourcePermit(String url, String role) {
        this.url = urlPrefixMarcher+url.replaceAll("\\{[^}]*\\}", "*");
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourcePermit that = (ResourcePermit) o;
        return Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(role);
    }
}
