package com.spring.cloud.global;

import java.util.List;

public interface ResourceRegister {
    public void registerEndpoint(List<ResourceDefine> endpoints);
}
