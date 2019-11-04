package com.spring.cloud;
import com.spring.cloud.entity.User;
import com.spring.cloud.service.UserService;
import com.spring.cloud.utils.RequestUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.SecurityUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.handler.DefaultWebFilterChain;
import org.springframework.web.server.handler.FilteringWebHandler;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/home")
    public String home() {
        SecurityUser user = RequestUserUtils.currentUser();
        System.err.println(user.getUsername());
        return "Hello world:"+user.getId();
    }

    @RequestMapping("/index")
    public String index() {
        List<User> all = userService.findAll();
        for (User u : all) {
            System.err.println(u.getId());
        }
        return "Hello index";
    }

    @RequestMapping(value = "/findUserByName",method = RequestMethod.POST)
    public SecurityUser findUserByName(String username) {
        User user = userService.findUserByName(username);
        if (user == null) {
            return null;
        }
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(user.getUsername());
        securityUser.setPassword(user.getPassword());
        securityUser.setId(user.getId());
        securityUser.setRoleId(user.getRoleId());
        return securityUser;
    }

}

