package com.spring.cloud.support.mvc;
import java.util.List;
import java.util.Map;

public interface ResourceRegister {
    public void registerEndpoint(Map<String, List<Map<String, String>>> endpoints);
}
