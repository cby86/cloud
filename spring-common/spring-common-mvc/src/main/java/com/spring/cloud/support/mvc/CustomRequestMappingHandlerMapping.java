package com.spring.cloud.support.mvc;

import com.spring.cloud.global.ResourceDefine;
import com.spring.cloud.global.ResourceRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 创建自定义requestMapping类来配置规则
 */
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private ResourceRegister resourceRegister;

    @Value("${spring.application.name}")
    protected String appName;
    @Value("${spring.application.desc:}")
    protected String discription;

    public CustomRequestMappingHandlerMapping(ResourceRegister resourceRegister) {
        this.resourceRegister = resourceRegister;
        this.setOrder(0);
    }

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


    public void doRegister() {
        List<ResourceDefine> endpointInfo = getResourceDefines();
        if (!CollectionUtils.isEmpty(endpointInfo)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    resourceRegister.registerEndpoint(endpointInfo);
                }
            }).start();
        }
    }

    public List<ResourceDefine> getResourceDefines() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = this.getHandlerMethods();
        List<ResourceDefine> endpointInfo = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod method = entry.getValue();
            Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
            for (String url : patterns) {
                ResourceDefine endpoint = getEndpoint(url, method);
                if (endpoint == null) {
                    continue;
                }
                endpointInfo.add(endpoint);
            }
        }
        endpointInfo.addAll(resourceRegister.getCustomerResource(this.appName,this.discription));
        return endpointInfo;
    }

    private ResourceDefine getEndpoint(String url, HandlerMethod method) {
        ResourceDesc resourceDesc = AnnotationUtils.findAnnotation(method.getMethod(), ResourceDesc.class);
        if (resourceDesc == null) {
            return null;
        }
        return new ResourceDefine(appName, discription, url, resourceDesc.model(), resourceDesc.name(), resourceDesc.desc(), String.valueOf(resourceDesc.version()));
    }

}
