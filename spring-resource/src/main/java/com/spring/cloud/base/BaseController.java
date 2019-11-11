package com.spring.cloud.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 10:38
 **/
public class BaseController {

    protected Map<String, Object> resultMap(String status, String message, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("msg", message);
        map.put("data", data);
        return map;
    }


    protected Pageable getPageResult(Integer currentPage, Integer pageSize) {
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }
        return  PageRequest.of(currentPage - 1, pageSize);
    }
}
