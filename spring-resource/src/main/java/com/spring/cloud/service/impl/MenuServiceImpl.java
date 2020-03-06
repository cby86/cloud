package com.spring.cloud.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.cloud.message.MessageType;
import com.spring.cloud.service.EventBaseProcessor;
import com.spring.cloud.entity.Menu;
import com.spring.cloud.exception.BusinessException;
import com.spring.cloud.message.MessageApplicationEvent;
import com.spring.cloud.repository.MenuRepository;
import com.spring.cloud.service.MenuService;
import com.spring.cloud.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class MenuServiceImpl extends EventBaseProcessor implements MenuService {

    @Autowired
    MenuRepository menuRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    @Override
    public void saveOrUpdate(Menu menu) {
        if (!StringUtils.isEmpty(menu.getId())) {
            this.publishMqEvent(new MessageApplicationEvent(JsonUtils.toJsonString(menu), MessageType.MenuChange.name()));
        }
        menuRepository.save(menu);
    }

    @Override
    public void deletedMenu(String id) {
        Menu menu = findMenuById(id);
        if (!menu.getChildren().isEmpty()) {
            throw new BusinessException("菜单下还存在子菜单,请先删除子菜单");
        }
        menuRepository.deleteById(id);
        this.publishMqEvent(new MessageApplicationEvent(id,MessageType.MenuDelete.name()));
    }

    @Override
    @Transactional(readOnly = true)
    public Menu findMenuById(String id) {
        return menuRepository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<Menu> findAllMenu() {
        return menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            predicates.add(criteriaBuilder.equal(root.get("menuType"), 0));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

    @Override
    @Transactional(readOnly = true)
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
