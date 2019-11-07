package com.spring.cloud.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author panyuanjun
 * @Date 2019/11/7/007 11:00
 **/
@FeignClient(value = "spring-role",fallback = RoleFallBack.class)
public interface RoleInterface {
    @RequestMapping(value = "/findRoles", method = RequestMethod.POST, produces = "application/json")
    List<Role> findRoles();
    @RequestMapping(value = "/findRoleById", method = RequestMethod.POST, produces = "application/json")
    Role findRoleById(String roleId);
}
