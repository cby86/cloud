package com.spring.cloud.controller;
import com.spring.cloud.entity.User;
import com.spring.cloud.service.UserService;
import com.spring.cloud.utils.RequestUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.SecurityUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/home")
    public String home() {
//        SecurityUser user = RequestUserUtils.currentUser();
//        System.err.println(user.getUsername());
        return "dsafdf";
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
    public User findUserByName(String username) {
        User user = userService.findUserByName(username);
        if (user == null) {
            return null;
        }
        return user;
    }

}

