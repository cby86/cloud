package com.spring.cloud.service.impl;

import com.spring.cloud.entity.Authentication;
import com.spring.cloud.repository.AuthenticationRepository;
import com.spring.cloud.repository.RoleRepository;
import com.spring.cloud.service.AuthenticationService;
import com.spring.cloud.service.dto.MenuMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Authentication> findAuthenticationByMenuId(String menuId) {
        return roleRepository.findAuthenticationByMenuId(menuId);
    }

    public void saveAll(List<Authentication> authentications) {
        if (!CollectionUtils.isEmpty(authentications)) {
            authenticationRepository.saveAll(authentications);
        }
    }

    @Override
    public void deleteAllAuthentication(String menuId) {
        authenticationRepository.deleteAllAuthenticationByMenuId(menuId);
    }

    @Override
    public void updateAuthentications(MenuMessage menuMessage) {
        List<Authentication> authenticationList=this.findAuthenticationByMenuId(menuMessage.getId());
        if (!CollectionUtils.isEmpty(authenticationList)) {
            authenticationList.forEach(authentication -> {
                authentication.setSort(menuMessage.getSort());
                authentication.setIcon(menuMessage.getIcon());
                if (!StringUtils.isEmpty(menuMessage.getParentId())) {
                    authentication.setParentId(menuMessage.getParentId());
                }
                authentication.setUrl(menuMessage.getUrl());
                authentication.setName(menuMessage.getName());
            });
            this.saveAll(authenticationList);
        }
    }

    @Override
    public void clearAuthentication() {
        List<Authentication> allAuthentication = authenticationRepository.findAllAuthentication();
        authenticationRepository.deleteAll(allAuthentication);
    }
}
