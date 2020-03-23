package com.spring.cloud.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.Set;

@FeignClient(value = "spring-user", fallback = UserFallBack.class)
public interface UserInterface  {
    @RequestMapping(value = "/authentication/loadAuthentications", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Set<String>> loadAuthentications();
}
