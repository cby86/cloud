package com.spring.cloud;
import com.spring.cloud.utils.RequestUserUtils;
import org.springframework.security.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    @RequestMapping("/home")
    public String home() {
        User user = RequestUserUtils.currentUser();
        System.err.println(user.getUsername());
        return "Hello world3";
    }

    @RequestMapping("/index")
    public String index() {
        return "Hello index";
    }

}

