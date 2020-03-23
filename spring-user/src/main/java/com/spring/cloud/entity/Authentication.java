package com.spring.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.cloud.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "mb_hunter_authentication")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Authentication extends BaseEntity {
    /**
     * 资源ID
     */
    private String menuId;
    /**
     * 资源名称
     */
    private String name;

    /**
     * 0 ：菜单授权；功能授权
     */
    private int authentionType;

    /**
     * 路径
     */
    private String url;

    /**
     * 权限内部关系
     */
    private String parentId;


    private String icon;

    private String code;
    private int sort;

    @Column(columnDefinition="tinyint default 1")
    private int forPrivate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "autentication_id")
    @JsonIgnore
    List<AuthenticationDetails> authenticationDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    public void addAuthenticationDetails(String systemResource) {
        if (authenticationDetails == null) {
            authenticationDetails = new ArrayList<>();
        }
        authenticationDetails.add(new AuthenticationDetails(systemResource));
    }

    public void addAllAuthenticationDetails(List<String> urls) {
        if (!CollectionUtils.isEmpty(urls)) {
            urls.forEach(url->{
                this.addAuthenticationDetails(url);
            });
        }
    }

    public  List<AuthenticationDetails> removeAuthenticationDetails(List<String> urls) {
        List<AuthenticationDetails> authenticationDetailsList = this.getAuthenticationDetails();
        List<AuthenticationDetails> deleteDetails = authenticationDetailsList.stream().
                filter(item -> urls.contains(item.getSystemResource())).collect(Collectors.toList());
        return deleteDetails;
    }
}
