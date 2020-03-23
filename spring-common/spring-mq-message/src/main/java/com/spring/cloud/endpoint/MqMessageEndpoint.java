package com.spring.cloud.endpoint;

import com.spring.cloud.entity.Event;
import com.spring.cloud.global.ResourceDefine;
import com.spring.cloud.service.EventService;
import com.spring.cloud.support.mvc.CustomRequestMappingHandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;

import java.util.List;

/**
 *  默认开始端点
 *  management:
 *    endpoint:
 *    mq-message-info:
 *    enabled: false    禁用断点
 */
@Endpoint(id="mq-message-info")
public class MqMessageEndpoint {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventService eventService;

    @ReadOperation
    public  List<Event> messsageInfo() {
        return eventService.findAllEvent();
    }
    @WriteOperation
    public String clear() {
        eventService.clear();
        return "success";
    }
}
