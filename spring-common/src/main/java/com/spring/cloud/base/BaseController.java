package com.spring.cloud.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author panyuanjun
 * @Date 2019/11/11/011 10:38
 **/
public class BaseController {
    public final static int DEFAULTSTATUS = 0;
    public final static String DEFAULTMESSAGE = "success";
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
}
