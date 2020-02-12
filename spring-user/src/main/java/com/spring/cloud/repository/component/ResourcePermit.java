package com.spring.cloud.repository.component;
import java.io.Serializable;

public class ResourcePermit implements Serializable{
    private String url;
    private String roles;


    public ResourcePermit(String url, String roles) {
        this.url = url;
        this.roles = roles;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
