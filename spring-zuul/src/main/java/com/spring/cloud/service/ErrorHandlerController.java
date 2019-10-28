package com.spring.cloud.service;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class ErrorHandlerController implements ErrorController {
    @RequestMapping("/error")
    public Object error(HttpServletRequest request, HttpServletResponse response) {
        if (response.getStatus() == 401) {
            return "没有授权";
        }
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulException exception = (ZuulException)ctx.getThrowable();
        exception.printStackTrace();
        if (exception.getCause().getMessage().contains("Forwarding error")) {
            return "服务繁忙，请稍后再试";
        }
        Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (status == 404) {
            return "服务不可以用，请稍后再试";
        }
        if (status == 500) {
            return "服务错误，请稍后再试";
        }
        return "服务繁忙，请稍后再试";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
