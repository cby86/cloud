package com.spring.cloud.service.impl;

import com.spring.cloud.entity.Authentication;
import com.spring.cloud.entity.User;
import com.spring.cloud.exception.BusinessException;
import com.spring.cloud.repository.UserRepository;
import com.spring.cloud.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User findUserByName(String username) {
        return userRepository.findUserByName(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findUserList(String name, Pageable pageable) {
        pageable.getSort().and(Sort.by(Sort.Order.desc("createDate")));
        return userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            if (StringUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.equal(root.get("username"), name));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(String userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public void saveOrUpdate(User user) {
        userRepository.save(user);
    }

    @Override
    public void deletedUser(String uerId) {
        User user = this.findUserById(uerId);
        if (user.isSystem()) {
            throw new BusinessException("内置用户支持删除");
        }
        userRepository.deleteById(uerId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasSameName(String userId, String username) {
        return userRepository.count((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            if (StringUtils.isNotEmpty(userId)) {
                predicates.add(criteriaBuilder.notEqual(root.get("id"), userId));
            }
            predicates.add(criteriaBuilder.equal(root.get("username"), username));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Authentication> findAuthentication(String userId) {
        return userRepository.findAuthentication(userId);
    }
}
