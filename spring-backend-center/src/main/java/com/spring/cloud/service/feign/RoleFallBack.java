package com.spring.cloud.service.feign;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 10:20
 **/
@Component
public class RoleFallBack implements RoleService{
    @Override
    public Map<String, Object> findRoles() {
        return null;
    }
}
