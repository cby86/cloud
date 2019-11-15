package com.spring.cloud.support.mvc;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * 创建自定义requestMapping类来配置规则
 */
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private ResourceRegister resourceRegister;

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomMethodCondition(Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }

    private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVersionCondition(apiVersion.value());
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
        Map<String, List<Map<String, String>>> endpointInfo = new HashMap<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod method = entry.getValue();
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                Map<String, String> endpoint = getEndpoint(method);
                if (endpoint == null) {
                    continue;
                }
                List<Map<String, String>> endpointDesc;
                if (endpointInfo.containsKey(url)) {
                    endpointDesc = endpointInfo.get(url);
                } else {
                    endpointDesc = new ArrayList<>();
                }
                endpointDesc.add(endpoint);
                endpointInfo.put(url, endpointDesc);
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                resourceRegister.registerEndpoint(endpointInfo);
            }
        }).start();
    }

    private Map<String, String> getEndpoint(HandlerMethod method) {
        Map<String, String> endpoint = new HashMap<>();
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method.getMethod(), ApiVersion.class);
        ResourceDesc resourceDesc = AnnotationUtils.findAnnotation(method.getMethod(), ResourceDesc.class);
        if (resourceDesc == null) {
            return null;
        }
        String model = "默认";
        String name = "默认";
        String desc = "默认";
        int version = 0;
        if (resourceDesc != null) {
            model = resourceDesc.model();
            name = resourceDesc.name();
            desc = resourceDesc.desc();
        }
        if (apiVersion != null) {
            version = apiVersion.value();
        }
        endpoint.put("model", model);
        endpoint.put("name", name);
        endpoint.put("desc", desc);
        endpoint.put("version", String.valueOf(version));
        return endpoint;
    }

    public void setResourceRegister(ResourceRegister resourceRegister) {
        this.resourceRegister = resourceRegister;
    }

}
