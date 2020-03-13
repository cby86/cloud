package com.spring.cloud.service.impl;

import com.spring.cloud.entity.App;
import com.spring.cloud.entity.Resource;
import com.spring.cloud.global.ResourceDefine;
import com.spring.cloud.repository.AppRepository;
import com.spring.cloud.repository.ResourceRepository;
import com.spring.cloud.service.ResourceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
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

@Service
@Transactional
@Primary
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    AppRepository appRepository;

    @Autowired
    ResourceRepository resourceRepository;
    @Override
    public void registerEndpoint(List<ResourceDefine> resource) {
        if (resource == null && resource.isEmpty()) {
            return;
        }
        List<App> apps = new ArrayList<>();
        for (ResourceDefine res : resource) {
            App app = new App(res.getApp(),res.getDescription());
            Resource rs = new Resource(res.getUrl(), res.getModel(), res.getName(), res.getDesc(), res.getVersion());
            int index= apps.indexOf(app);
            if (index!=-1) {
                app = apps.get(index);
                app.addResource(rs);
            }else {
                app.addResource(rs);
                apps.add(app);
            }
        }

        for (App app : apps) {
            //删除对应的注册
            App appIndb = appRepository.findByName(app.getName());
            if (appIndb != null) {
                List<Resource> overDueResource = appIndb.mergeResources(app.getResourceList());
                appRepository.save(appIndb);
                resourceRepository.deleteInBatch(overDueResource);
            }else {
                appRepository.save(app);
            }
        }
    }


    @Override
    public void resourceDeleteById(String resourceId) {
        resourceRepository.deleteById(resourceId);
    }

    @Override
    public Resource findResourceById(String resourceId) {
       return resourceRepository.getOne(resourceId);
    }

    @Override
    public void saveResource(Resource resource) {
        resourceRepository.save(resource);
    }

    @Override
    public Page<Resource> findResourcePageList(String appName,String name,String url,Pageable pageable) {
        pageable.getSort().and(Sort.by(Sort.Order.desc("createDate")));
        return resourceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Join<Object, Object> app = root.join("app", JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            if (StringUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
            }
            if (StringUtils.isNotEmpty(appName)) {
                predicates.add(criteriaBuilder.equal(app.get("name"), appName));
            }
            if (StringUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
            }
            if (StringUtils.isNotEmpty(url)) {
                predicates.add(criteriaBuilder.equal(root.get("url"), url));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }

    @Override
    public Page<Resource> findBindResource(String appName,String name, String url, String menuId, Pageable pageable) {
        pageable.getSort().and(Sort.by(Sort.Order.desc("createDate")));
        return resourceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            Join<Object, Object> menus = root.join("menus", JoinType.LEFT);
            Join<Object, Object> app = root.join("app", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(menus.get("id"), menuId));
            if (StringUtils.isNotEmpty(appName)) {
                predicates.add(criteriaBuilder.equal(app.get("name"), appName));
            }
            if (StringUtils.isNotEmpty(name)) {
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
            }
            if (StringUtils.isNotEmpty(url)) {
                predicates.add(criteriaBuilder.equal(root.get("url"), url));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
    }
}
