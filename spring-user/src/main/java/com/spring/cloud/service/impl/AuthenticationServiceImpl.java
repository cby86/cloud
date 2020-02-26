package com.spring.cloud.service.impl;

import com.spring.cloud.entity.Authentication;
import com.spring.cloud.repository.AuthenticationRepository;
import com.spring.cloud.repository.RoleRepository;
import com.spring.cloud.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Authentication> findAuthenticationByMenuId(String menuId) {
        return roleRepository.findAuthenticationByMenuId(menuId);
//        return roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
//            predicates.add(criteriaBuilder.equal(root.get("menuId"), menuId));
//            Join<Object, Object> parent = root.join("role", JoinType.LEFT);
//            predicates.add(criteriaBuilder.isNotNull(parent));
//            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
//        });
    }

    @Override
    public void saveAll(List<Authentication> authentications) {
        if (!CollectionUtils.isEmpty(authentications)) {
            authenticationRepository.saveAll(authentications);
        }
    }

    @Override
    public void deleteAllAuthentication(String menuId) {
        authenticationRepository.deleteAllAuthenticationByMenuId(menuId);
    }
}
