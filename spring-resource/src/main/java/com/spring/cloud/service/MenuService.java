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

    Page<Menu> findMenuPageList(String name,String url, int menuType, Pageable pageable);

    void saveMenu(Menu menu);

    List<Menu> findMenuByParentId(String parentId,String name,String url,String excludeMenuId);

    List<Menu> findAllMenu();

    boolean hasSame(String id, String name, String value);
}
