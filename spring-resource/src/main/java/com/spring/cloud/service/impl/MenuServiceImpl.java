package com.spring.cloud.service.impl;

import com.spring.cloud.entity.Menu;
import com.spring.cloud.exception.BusinessException;
import com.spring.cloud.repository.MenuRepository;
import com.spring.cloud.service.MenuService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Menu menu = findMenuById(id);
        if (!menu.getChildren().isEmpty()) {
            throw new BusinessException("菜单下还存在子菜单,请先删除子菜单");
        }
        menuRepository.deleteById(id);
    }

    @Override
    public Menu findMenuById(String id) {
        return menuRepository.getOne(id);
    }

    @Override
    public Page<Menu> findMenuPageList(String name, String url, int menuType, Pageable pageable) {
        pageable.getSort().and(Sort.by(Sort.Order.desc("createDate")));
        return menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
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
    public void saveMenu(Menu menu) {
        this.saveOrUpdate(menu);
    }

    @Override
    public List<Menu> findMenuByParentId(String parentId, String name, String url, String excludeMenuId) {
        return menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            predicates.add(criteriaBuilder.equal(root.get("menuType"), 0));
            Join<Object, Object> parent = root.join("parent", JoinType.LEFT);
            if (StringUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
            }
            if (StringUtils.isNotEmpty(url)) {
                predicates.add(criteriaBuilder.equal(root.get("url"), url));
            }
            if (StringUtils.isNotEmpty(parentId)) {
                predicates.add(criteriaBuilder.equal(parent.get("id"), parentId));
            } else {
                predicates.add(criteriaBuilder.isNull(parent));
            }
            if (StringUtils.isNotEmpty(excludeMenuId)) {
                predicates.add(criteriaBuilder.notEqual(root.get("id"), excludeMenuId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        },Sort.by(Sort.Order.asc("sort")));
    }

    @Override
    public List<Menu> findAllMenu() {
        return menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            predicates.add(criteriaBuilder.equal(root.get("menuType"), 0));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

    @Override
    public boolean hasSame(String id, String name,String value) {
        return menuRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            if (StringUtils.isNotEmpty(id)) {
                predicates.add(criteriaBuilder.notEqual(root.get("id"), id));
            }
            predicates.add(criteriaBuilder.equal(root.get(name), value));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }) > 0;
    }
}
