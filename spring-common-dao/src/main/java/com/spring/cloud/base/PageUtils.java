package com.spring.cloud.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public class PageUtils {
    public static Map<String, Object> responsePage(Page page) {
        HashMap<String, Object> data = new HashMap<>();
        if (page == null) {
            return data;
        }
        data.put("items", page.getContent());
        data.put("totalCount", page.getTotalElements());
        data.put("totalPage", page.getTotalPages());
        return data;
    }

    public static Pageable pageable(Integer page, Integer pageSize) {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        return PageRequest.of(page, pageSize);
    }
}
