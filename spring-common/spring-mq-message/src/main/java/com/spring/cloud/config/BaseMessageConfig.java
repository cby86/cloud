package com.spring.cloud.config;

import com.spring.cloud.service.EventService;
import com.spring.cloud.service.MessageService;
import com.spring.cloud.utils.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

public abstract class BaseMessageConfig {
    protected Logger logger = LoggerFactory.getLogger(BaseMessageConfig.class);
    protected final static int errorEventFetchSize = 100;
    protected static final int maxRetry = 3;

    @Autowired
    protected EventService eventService;
    @Autowired
    protected RedisLock redisLock;

    @Value("${spring.application.name}")
    protected String appName;


    @Scheduled(cron = "0/30 * * * * *")
    public void work() {
        String requestId = UUID.randomUUID().toString();
        String lockName = appName+getLockName();
        boolean alarmNotify = redisLock.lock(lockName, requestId, 10000);
        if (alarmNotify) {
            try {
                redoMessage();
            } finally {
                redisLock.unlock(lockName, requestId);
            }
        }
    }
    protected abstract String getLockName();

    protected abstract void redoMessage();



}
