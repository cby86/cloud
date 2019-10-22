package com.spring.cloud;
import com.spring.cloud.utils.RequestUserUtils;
import org.springframework.security.User;
import com.spring.cloud.utils.SecurityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

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

