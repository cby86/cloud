package com.spring.cloud.utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.SecurityUser;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUserUtils {
    public static final String USERHEADERKEY = "user";
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
