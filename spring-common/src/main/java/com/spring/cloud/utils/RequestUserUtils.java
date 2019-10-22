package com.spring.cloud.utils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUserUtils {
    public static final String USERHEADERKEY = "user";
    public static User currentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            String user = request.getHeader(USERHEADERKEY);
            User authUser = JSONObject.parseObject(user, User.class);
            return authUser;
        } catch (Exception ex) {
            return null;
        }
    }
}
