package com.spring.cloud.service.impl.message;

import com.spring.cloud.service.MessageService;
import com.spring.cloud.service.impl.message.handler.MessageHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService, ApplicationContextAware {

    private Map<String, MessageHandler> handler;

    @Override
    public void doProcess(int eventId, String sourceId, Object message, String messageType) {
        if (handler == null || handler.get(messageType) == null) {
            throw new UnsupportedOperationException("不支持消息处理");
        }
        MessageHandler messageHandler = handler.get(messageType);
        messageHandler.doProcess(eventId,sourceId,message);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        handler = new HashMap<>();
        Map<String, MessageHandler> beanMap = applicationContext.getBeansOfType(MessageHandler.class);
        for (Map.Entry<String, MessageHandler> bean : beanMap.entrySet()) {
            handler.put(bean.getValue().supportMessageType(), bean.getValue());
        }
    }
}
