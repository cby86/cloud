package com.spring.cloud.utils;
import com.alibaba.fastjson.JSONObject;
import com.spring.cloud.security.SecurityUser;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUserUtils {
    public static final String USERHEADERKEY = "Authorization";
    public static SecurityUser currentUser() {
        try {
                HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
            String userInfo = request.getHeader(USERHEADERKEY);
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
            String username = jsonObject.getString("username");
            if (StringUtils.isEmpty(username)) {
                return null;
            }
            SecurityUser user = new SecurityUser();
            user.setUsername(username);
            user.setId(jsonObject.getString("id"));
            return user;
        } catch (Exception ex) {
            return null;
        }
    }
}
