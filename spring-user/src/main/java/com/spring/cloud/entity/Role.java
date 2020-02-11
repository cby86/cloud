package com.spring.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mb_hunter_role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","fieldHandler"})
public class Role extends BaseEntity {

    private String name;

    private String code;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private List<Authentication> authentications;

    public List<Authentication> getAuthentications() {
        return authentications;
    }

    public void setAuthentications(List<Authentication> authentications) {
        this.authentications = authentications;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
