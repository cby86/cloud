package com.spring.cloud;
import org.springframework.security.User;
import com.spring.cloud.utils.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    @RequestMapping("/home")
    public String home() {
        return "Hello world3";
    }

    @RequestMapping("/index")
    public String index() {
        return "Hello index";
    }

}

