package com.spring.cloud.service.support.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenbangyuan on 2017/5/25.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 系统异常处理，比如：404,500
     * @param request
     * @param ex
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map defaultErrorHandler(HttpServletRequest request,HttpServletResponse response, Exception ex) throws Exception {
        if (isAjax(request)) {
            //如果是ajax请求，设置状态码，让ajax执行错误处理流程
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        HashMap hashMap = new HashMap();
        hashMap.put("status", 0);
        log.error("异常",ex);
        String message = "系统内部错误";
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            message = "不支持的访问方式";
        }
        if (ex instanceof NoHandlerFoundException) {
            message = "资源不存在";
        }
        if (ex instanceof MultipartException) {
            message = "上传文件超出了限制,单个文件10M以内";
        }
        if (ex instanceof UnsupportedOperationException) {
            message = ex.getMessage();
        }
        if (ex instanceof AuthenticationException) {
            message = ex.getMessage();
        }
        hashMap.put("message", message);
        return hashMap;
    }

    public boolean isAjax(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        return StringUtils.hasText(requestType) && requestType.equals("XMLHttpRequest");
    }

}
