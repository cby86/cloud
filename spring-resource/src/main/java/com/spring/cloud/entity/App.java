package com.spring.cloud.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "mb_hunter_app")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class App extends BaseEntity {
    private String name;
    private String description;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "app_id")
    private List<Resource> resourceList;

    public App() {
    }
    public App(String appName,String description) {
        this.name = appName;
        this.description = description;
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

    public List<Resource> mergeResources(List<Resource> resourceList) {
        List<Resource> overDueResource = new ArrayList<>();
        List<Resource> oldResource = this.getResourceList();
        Iterator<Resource> iterator = oldResource.iterator();
        while (iterator.hasNext()) {
            Resource next = iterator.next();
            int index = resourceList.indexOf(next);
            if (index > -1) {
                Resource resource = resourceList.get(index);
                next.marge(resource);
                resourceList.remove(resource);
            }else {
                overDueResource.add(next);
                iterator.remove();
            }
        }
        oldResource.addAll(resourceList);
        return overDueResource;
    }
}
