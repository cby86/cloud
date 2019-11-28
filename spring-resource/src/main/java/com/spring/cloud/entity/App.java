package com.spring.cloud.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mb_hunter_app")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class App extends BaseEntity {
    private String name;

    private String host;

    private int port;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id")
    private List<Resource> resourceList;

    public App() {
    }

    public App(String appName, String host, int port) {
        this.name = appName;
        this.host = host;
        this.port = port;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        App app = (App) o;
        return Objects.equals(name, app.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    public void addResource(Resource resource) {
        if (this.resourceList == null) {
            this.resourceList = new ArrayList<>();
        }
        this.resourceList.add(resource);
    }
}
