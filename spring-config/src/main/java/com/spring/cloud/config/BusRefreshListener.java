package com.spring.cloud.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.RefreshListener;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.context.refresh.ContextRefresher;

public class BusRefreshListener extends RefreshListener {
    @Autowired
    private ServiceMatcher serviceMatcher;
    public BusRefreshListener(ContextRefresher contextRefresher) {
        super(contextRefresher);
}
    @Override
    public void onApplicationEvent(RefreshRemoteApplicationEvent event) {
        //只有刷新自己时才进行处理
        if (serviceMatcher.isForSelf(event)) {
            super.onApplicationEvent(event);

        }
    }
}
