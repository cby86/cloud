package com.spring.cloud.service.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spring.cloud.entity.App;
import com.spring.cloud.entity.Resource;
import com.spring.cloud.repository.AppRepository;
import com.spring.cloud.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    AppRepository appRepository;
    @Override
    public void register(List<String> resource) {
        if (resource == null && resource.isEmpty()) {
            return;
        }
        List<App> apps = new ArrayList<>();
        for (String res : resource) {
            JSONObject appInfo = JSON.parseObject(res);
            String appName = appInfo.getString("app");
            String host = appInfo.getString("host");
            int port = appInfo.getInteger("port");
            String url = appInfo.getString("url");
            String model = appInfo.getString("model");
            String name = appInfo.getString("name");
            String desc = appInfo.getString("desc");
            String version = appInfo.getString("version");
            App app = new App(appName, host, port);
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
}
