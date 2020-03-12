package com.spring.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mb_hunter_authentication_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class AuthenticationDetails extends BaseEntity {
    private String systemResource;

    public AuthenticationDetails() {
    }

    public AuthenticationDetails(String systemResource) {
        this.systemResource = systemResource;
    }
}
