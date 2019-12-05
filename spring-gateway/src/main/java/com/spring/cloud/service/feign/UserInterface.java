package com.spring.cloud.service.feign;

import com.spring.cloud.service.feign.component.ResourcePermit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "spring-user", fallback = UserFallBack.class)
public interface UserInterface {
    @RequestMapping(value = "/authentication/loadAuthentications", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ResourcePermit> loadAuthentications();
}
