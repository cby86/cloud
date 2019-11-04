package com.spring.cloud.service.impl;
import com.spring.cloud.entity.Role;
import com.spring.cloud.service.RoleService;
import com.spring.cloud.service.feign.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserInterface userInterface;
    @Autowired
    private RoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = userInterface.findUserByName(username);
        if (securityUser!=null && !StringUtils.isEmpty(securityUser.getRoleId())) {
            Role role = roleService.findRoleById(securityUser.getRoleId());
            securityUser.addAuthority(role.getCode());
        }
        return securityUser;
    }
}
