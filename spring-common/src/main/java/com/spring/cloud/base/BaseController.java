package com.spring.cloud.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 10:38
 **/
public class BaseController  implements ApplicationContextAware{
    public final static int DEFAULTSTATUS = 0;
    public final static String DEFAULTMESSAGE = "success";
    protected ApplicationContext applicationContext;

    protected Map<String, Object> resultMap(int status, String message, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("msg", message);
        map.put("data", data);
        return map;
    }
    protected Map<String, Object> resultMap(Object data) {
        return this.resultMap(DEFAULTSTATUS, DEFAULTMESSAGE, data);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected void publishEvent(ApplicationEvent applicationEvent) {
        this.applicationContext.publishEvent(applicationEvent);
    }
}
