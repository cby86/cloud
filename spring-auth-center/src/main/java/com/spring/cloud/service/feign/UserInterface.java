package com.spring.cloud.service.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "spring-user",fallback = UserFallBack.class)
public interface UserInterface {
    @RequestMapping(value = "/home", method = RequestMethod.GET, produces = "application/json")
    public String home();

    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = "application/json")
    public String index();
}
