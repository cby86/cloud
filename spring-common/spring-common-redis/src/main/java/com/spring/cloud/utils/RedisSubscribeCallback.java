package com.spring.cloud.utils;

 
public interface RedisSubscribeCallback {
    void callback(String msg);
}
