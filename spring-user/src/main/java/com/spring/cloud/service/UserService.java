package com.spring.cloud.service;

import com.spring.cloud.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findUserByName(String username);
}
