package com.spring.cloud.service.impl;
import com.spring.cloud.entity.User;
import com.spring.cloud.repository.UserRepository;
import com.spring.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByName(String username) {
        return userRepository.findUserByName(username);
    }
}
