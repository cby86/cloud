package com.spring.cloud.service;

import com.spring.cloud.entity.App;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppService {
    Page<App> findAppPageList(String name, Pageable pageable);
}
