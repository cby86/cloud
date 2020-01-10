package com.spring.cloud.service;

import com.spring.cloud.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 10:44
 **/
public interface MenuService {

    void saveOrUpdate(Menu menu);

    void deletedMenu(String id);

    Menu findMenuById(String id);

    List<Menu> findMenuList(int menuTyp);

    Page<Menu> findMenuPageList(String name,String url, int menuType, Pageable pageable);

    List<Menu> findMenuByIds(String menuIds);

    void saveMenu(String id, String name, int menuType, String url, String parentId);
}
