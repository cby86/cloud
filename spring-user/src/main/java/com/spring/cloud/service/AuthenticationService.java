package com.spring.cloud.service;

import com.spring.cloud.service.dto.MenuMessage;

public interface AuthenticationService {

    void deleteAllAuthentication(String menuId);

    void updateAuthentications(MenuMessage menuMessage);
}
