package com.spring.cloud.service;

import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User findUserByName(String username);

    Page<User> findUserList(String name, Pageable pageable);

    User findUserById(String userId);

    void saveOrUpdate(User user);

    void deletedUser(String uerId);

    boolean hasSameName(String userId, String username);

    List<Authentication> findAuthentication(String userId);
}
