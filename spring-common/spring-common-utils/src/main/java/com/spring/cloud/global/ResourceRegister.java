package com.spring.cloud.global;

import java.util.Collections;
import java.util.List;

public interface ResourceRegister {
    default List<ResourceDefine> getCustomerResource(String app,String appDesc){
        return Collections.emptyList();
    }

    public void registerEndpoint(List<ResourceDefine> endpoints);
}
