package com.spring.cloud.service.feign;

import com.spring.cloud.security.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @Author panyuanjun
 * @Date 2019/11/7/007 11:00
 **/
@FeignClient(value = "spring-resource",fallback = RoleFallBack.class)
public interface RoleInterface {
    @RequestMapping(value = "/findRoleById", method = RequestMethod.POST, produces = "application/json")
    Role findRoleById(@RequestParam(value = "roleId") String roleId);
}
