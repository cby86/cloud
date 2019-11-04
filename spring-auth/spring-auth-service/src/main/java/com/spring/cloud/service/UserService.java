package com.spring.cloud.service;

import com.spring.cloud.entity.User;
import org.springframework.security.SecurityUser;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findUserByName(String username);
}
