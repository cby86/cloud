package com.spring.cloud.repository.component;
import java.io.Serializable;

public class ResourcePermit implements Serializable{
    private String url;
    private String version;
    private String roles;


    public ResourcePermit(String url, String version, String roles) {
        this.url = url;
        this.version = version;
        this.roles = roles;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
