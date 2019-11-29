package com.spring.cloud.service.impl;
import com.spring.cloud.service.UserService;
import com.spring.cloud.service.feign.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserInterface userInterface;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userInterface.findUserByName(username);
    }
}
