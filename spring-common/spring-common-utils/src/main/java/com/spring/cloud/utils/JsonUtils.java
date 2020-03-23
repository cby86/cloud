package com.spring.cloud.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true).
                    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String toJsonString(Object target) {
        try {
            return objectMapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException("对象不支持转换Json:"+e.getMessage());
        }
    }

    public static <T> T jsonToObject(String target, Class<T> type) {

        try {
            return objectMapper.readValue(target,type);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException("Json不支持转换对象:" + e.getMessage());
        }
    }

    public static <T> T jsonToObjectList(String target, TypeReference<T> type) {
        try {
            return objectMapper.readValue(target, type);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException("Json不支持转换对象:" + e.getMessage());
        }
    }

}
