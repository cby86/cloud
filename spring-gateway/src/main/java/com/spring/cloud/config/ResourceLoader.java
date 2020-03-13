package com.spring.cloud.config;

import java.util.Map;
import java.util.Set;

public interface ResourceLoader {
    Map<String, Set<String>> loadResource();
}
