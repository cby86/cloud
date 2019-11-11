package com.spring.cloud.service.impl;

import com.spring.cloud.entity.Menu;
import com.spring.cloud.repository.MenuRepository;
import com.spring.cloud.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 10:48
 **/
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;


    @Override
    public void saveOrUpdate(Menu menu) {

    }

    @Override
    public void deletedMenu(String id) {

    }

    @Override
    public Menu findMenuById(String id) {
        return null;
    }

    @Override
    public List<Menu> findMenuList(int menuTyp) {
        return null;
    }

    @Override
    public Page<Menu> findMenuPageList(String name, int menuType, Pageable pageable) {
        return null;
    }

    @Override
    public List<Menu> findMenuByIds(String menuIds) {
        return null;
    }
}
