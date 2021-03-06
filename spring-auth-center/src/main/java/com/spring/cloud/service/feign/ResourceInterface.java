package com.spring.cloud.service.feign;

import com.spring.cloud.global.ResourceDefine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "spring-resource", fallback = ResourceFallBack.class)
public interface ResourceInterface {
    @RequestMapping(value = "/resource/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void registerEndpoint(List<ResourceDefine> endpoints);
}
