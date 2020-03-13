package com.spring.cloud.repository.component;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class ResourcePermit implements Serializable {
    private String url;
    private int permit;
    private String role;

    public ResourcePermit(String url,int permit, String role) {
        this.permit = permit;
        this.url = url;
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
