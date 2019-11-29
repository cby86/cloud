package com.spring.cloud.service;
import com.spring.cloud.entity.App;

import java.util.List;

public interface ResourceService {
    void register(List<String> resource);
    App getAppByName(String appName);
}
