package com.spring.cloud.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spring.cloud.entity.App;
import com.spring.cloud.entity.Resource;
import com.spring.cloud.exception.BusinessException;
import com.spring.cloud.repository.AppRepository;
import com.spring.cloud.repository.ResourceRepository;
import com.spring.cloud.service.ResourceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    AppRepository appRepository;

    @Autowired
    ResourceRepository resourceRepository;
    @Override
    public void register(List<String> resource) {
        if (resource == null && resource.isEmpty()) {
            return;
        }
        List<App> apps = new ArrayList<>();
        for (String res : resource) {
            JSONObject appInfo = JSON.parseObject(res);
            String appName = appInfo.getString("app");
            String url = appInfo.getString("url");
            String model = appInfo.getString("model");
            String name = appInfo.getString("name");
            String desc = appInfo.getString("desc");
            String version = appInfo.getString("version");
            String description = appInfo.getString("description");
            App app = new App(appName,description);
            Resource rs = new Resource(url,model,name,desc,version);
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
                appRepository.delete(appIndb);
            }
            //重新注册
            appRepository.save(app);
        }
    }

    @Override
    public App getAppByName(String appName) {
        return appRepository.findByName(appName);
    }

    @Override
    public List<Resource> findResourceByAppId(String appId) {
        return resourceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Object, Object> app = root.join("app", JoinType.LEFT);
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            predicates.add(criteriaBuilder.equal(app.get("id"), appId));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
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
}
