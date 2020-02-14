package com.spring.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mb_hunter_role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","fieldHandler"})
@Getter
@Setter
public class Role extends BaseEntity {

    private String name;

    private String code;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private List<Authentication> authentications;

    /**
     * 内置用户
     */
    private boolean system;

    public void addAuthentication(Authentication authentication) {
        if (CollectionUtils.isEmpty(authentications)) {
            authentications = new ArrayList<>();
        }
        authentications.add(authentication);
    }
}
