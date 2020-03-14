package com.spring.cloud.service;

import com.spring.cloud.service.dto.MenuMessage;

import java.util.List;

public interface AuthenticationService {

    void deleteAllAuthentication(String menuId);

    void updateAuthentications(MenuMessage menuMessage);

    void clearAuthentication();

    void deleteAuthenticationDetails(String menuId, List<String> urls);

    void addAuthenticationDetails(String menuId, List<String> urls);
}
