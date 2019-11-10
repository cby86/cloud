package com.spring.cloud.service.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.SecurityUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spring-user",fallback = UserFallBack.class)
public interface UserInterface {
    @RequestMapping(value = "/findUserByName", method = RequestMethod.POST, produces = "application/json")
    public SecurityUser findUserByName(@RequestParam(value = "username") String username);
}
