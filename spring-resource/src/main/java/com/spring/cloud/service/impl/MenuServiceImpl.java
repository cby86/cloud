package com.spring.cloud.service.impl;

import com.spring.cloud.entity.Menu;
import com.spring.cloud.repository.MenuRepository;
import com.spring.cloud.service.MenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
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
        menuRepository.save(menu);
    }

    @Override
    public void deletedMenu(String id) {
        menuRepository.deleteById(id);
    }

    @Override
    public Menu findMenuById(String id) {
        return menuRepository.getOne(id);
    }

    @Override
    public List<Menu> findMenuList(int menuType) {
        return menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("menuType"), menuType));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

    @Override
    public Page<Menu> findMenuPageList(String name,String url, int menuType, Pageable pageable) {
        return menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
            }
            if (StringUtils.isNotEmpty(url)) {
                predicates.add(criteriaBuilder.equal(root.get("url"), name));
            }
            if (menuType != -1) {
                predicates.add(criteriaBuilder.equal(root.get("menuType"), menuType));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }

    @Override
    public List<Menu> findMenuByIds(String menuIds) {
        return menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(menuIds)) {
                Expression<String> ids = root.get("id");
                CriteriaBuilder.In<Object> in = criteriaBuilder.in(ids);
                String[] split = menuIds.split(",");
                for (int i = 0; i < split.length; i++) {
                    in.value(split[i]);
                }
                predicates.add(in);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

    @Override
    public void saveMenu(Menu menu) {
        this.saveOrUpdate(menu);
    }
}
