package com.spring.cloud.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("status", 0);
        data.put("msg", "用户名或密码错误");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(JSONObject.toJSONString(data));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
