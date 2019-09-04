package com.spring.cloud;
import com.spring.cloud.model.User;
import com.spring.cloud.utils.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    @RequestMapping("/home")
    public String home() {
        User user = SecurityUtils.currentUser();

        System.out.println(user.getUsername());

        return "Hello world3";
    }

    @RequestMapping("/index")
    public String index() {
        return "Hello index";
    }

}

