package com.spring.cloud.service;

import com.spring.cloud.entity.Authentication;
import com.spring.cloud.service.dto.MenuMessage;

import java.util.List;

public interface AuthenticationService {

    void deleteAllAuthentication(String menuId);

    void updateAuthentications(MenuMessage menuMessage);

    void clearAuthentication();
}
