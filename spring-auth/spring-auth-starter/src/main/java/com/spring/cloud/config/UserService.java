package com.spring.cloud.config;
import com.spring.cloud.service.feign.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserInterface userInterface;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser userByName = userInterface.findUserByName(username);
        System.err.println(userByName);
        return userByName;
    }
}
