package com.spring.cloud.utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class RequestUserUtils {
    public static final String USERHEADERKEY = "user";
    public static User currentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            String userInfo = request.getHeader(USERHEADERKEY);
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
            User user = new User();
            user.setUsername(jsonObject.getString("username"));
            JSONArray authorities = jsonObject.getJSONArray("authorities");
            for (Object obj : authorities) {
                user.addAuthority(obj.toString());
            }
            return user;
        } catch (Exception ex) {
            return null;
        }
    }
}
