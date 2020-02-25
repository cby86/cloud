package com.spring.cloud.service;

import com.spring.cloud.entity.Authentication;

import java.util.List;

public interface AuthenticationService {
    List<Authentication> findAuthenticationByMenuId(String menuId);

    void saveAll(List<Authentication> authenticationList);

    void deleteAllAuthentication(String menuId);
}
