package com.spring.cloud.config;
import java.util.List;
import java.util.Map;

public interface ResourceLoader {
    Map<String, List<String>> loadResource();
}
