package com.spring.cloud.service.impl;
import com.spring.cloud.service.UserService;
import com.spring.cloud.service.feign.RoleInterface;
import com.spring.cloud.service.feign.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.CustomerSimpleGrantedAuthority;
import org.springframework.security.Role;
import org.springframework.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserInterface userInterface;
    @Autowired
    RoleInterface roleInterface;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser user = userInterface.findUserByName(username);
        if (!StringUtils.isEmpty(user.getRoleId())) {
            Role role = roleInterface.findRoleById(user.getRoleId());
            user.addAuthority(role.getCode());
        }
        return user;
    }
}
