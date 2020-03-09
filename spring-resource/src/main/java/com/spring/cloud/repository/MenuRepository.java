package com.spring.cloud.repository;

import com.spring.cloud.base.BaseRepository;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 10:44
 **/
public interface MenuRepository extends BaseRepository<Menu, String> {
}
