package com.spring.cloud.service.impl;

import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.Role;
import com.spring.cloud.repository.RoleRepository;
import com.spring.cloud.repository.component.ResourcePermit;
import com.spring.cloud.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author panyuanjun
 * @Date 2019/11/8/008 11:03
 **/
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void saveOrUpdate(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findRoleById(String id) {
        return roleRepository.findRoleById(id);
    }

    @Override
    public Page<Role> findRoleList(String name,String code, Pageable pageable) {
        pageable.getSort().and(Sort.by(Sort.Order.desc("createDate")));
        return roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            if (StringUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
            }
            if (StringUtils.isNotEmpty(code)) {
                predicates.add(criteriaBuilder.equal(root.get("code"), code));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }

    @Override
    public List<ResourcePermit> loadAuthentications() {
        return roleRepository.loadAuthentications();
    }

    @Override
    public boolean hasSameName(String id, String code) {
        return roleRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            if (StringUtils.isNotEmpty(id)) {
                predicates.add(criteriaBuilder.notEqual(root.get("id"), id));
            }
            predicates.add(criteriaBuilder.equal(root.get("code"), code));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }) > 0;
    }

    @Override
    public void deletedRole(String roleId) {
        roleRepository.deleteById(roleId);
    }

}
