package com.spring.cloud.service;

import com.spring.cloud.config.MenuMessage;
import com.spring.cloud.entity.Authentication;

import java.util.List;

public interface AuthenticationService {

    void deleteAllAuthentication(String menuId);

    void updateAuthentications(MenuMessage menuMessage);
}
