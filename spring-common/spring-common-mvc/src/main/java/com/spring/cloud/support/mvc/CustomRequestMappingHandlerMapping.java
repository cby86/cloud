package com.spring.cloud.support.mvc;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 创建自定义requestMapping类来配置规则
 */
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private ResourceRegister resourceRegister;

    @Value("${spring.application.name}")
    protected String appName;
    @Value("${spring.application.desc}")
    protected String discription;

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomTypeCondition(Class<?> handlerType) {
        ResourceDesc resourceDesc = AnnotationUtils.findAnnotation(handlerType, ResourceDesc.class);
        return createCondition(resourceDesc);
    }

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomMethodCondition(Method method) {
        ResourceDesc resourceDesc = AnnotationUtils.findAnnotation(method, ResourceDesc.class);
        return createCondition(resourceDesc);
    }

    private RequestCondition<ApiVersionCondition> createCondition(ResourceDesc resourceDesc) {
        return resourceDesc == null ? null : new ApiVersionCondition(resourceDesc.version());
    }


    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        super.registerHandlerMethod(handler, method, mapping);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        if (resourceRegister != null) {
            doRegister();
        }
    }

    private void doRegister() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.getHandlerMethods();
        List<String> endpointInfo = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod method = entry.getValue();
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                Map<String, String> endpoint = getEndpoint(url,method);
                if (endpoint == null) {
                    continue;
                }
                endpointInfo.add(JSON.toJSONString(endpoint));
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                resourceRegister.registerEndpoint(endpointInfo);
            }
        }).start();
    }

    private Map<String, String> getEndpoint(String url, HandlerMethod method) {
        Map<String, String> endpoint = new HashMap<>();
        ResourceDesc resourceDesc = AnnotationUtils.findAnnotation(method.getMethod(), ResourceDesc.class);
        if (resourceDesc == null) {
            return null;
        }
        endpoint.put("app", appName);
        endpoint.put("description", discription);
        endpoint.put("url", url);
        endpoint.put("model", resourceDesc.model());
        endpoint.put("name", resourceDesc.name());
        endpoint.put("desc", resourceDesc.desc());
        endpoint.put("version", String.valueOf(resourceDesc.version()));
        return endpoint;
    }

    public void setResourceRegister(ResourceRegister resourceRegister) {
        this.resourceRegister = resourceRegister;
    }

}
