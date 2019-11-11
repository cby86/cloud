package com.spring.cloud.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 10:19
 **/
@FeignClient(value = "spring-resource",fallback = RoleFallBack.class)
public interface RoleService {

    @RequestMapping(value = "/findRoles", method = RequestMethod.POST, produces = "application/json")
    Map<String, Object> findRoles();


}
