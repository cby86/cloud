package com.spring.cloud.config;

import com.spring.cloud.entity.Authentication;
import com.spring.cloud.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;


@EnableBinding(Sink.class)
@Configuration
public class MessageConfig {

    @Autowired
    AuthenticationService authenticationService;

    @StreamListener(target = Sink.INPUT,condition="headers['messageType']=='updateMenu'")
    void menuUpdate(@Payload MenuMessage menuMessage,@Header("messageType") String header) {
        List<Authentication> authenticationList=authenticationService.findAuthenticationByMenuId(menuMessage.getId());
        if (!CollectionUtils.isEmpty(authenticationList)) {
            authenticationList.forEach(authentication -> {
                authentication.setSort(menuMessage.getSort());
                authentication.setIcon(menuMessage.getIcon());
                if (!StringUtils.isEmpty(menuMessage.getParentId())) {
                    authentication.setParentId(menuMessage.getParentId());
                }
                authentication.setUrl(menuMessage.getUrl());
                authentication.setName(menuMessage.getMenuName());
            });
            authenticationService.saveAll(authenticationList);
        }

    }

    @StreamListener(target = Sink.INPUT,condition="headers['messageType']=='deleteMenu'")
    void deleteMenu(String menuId,@Header("messageType") String header) {
        if (!StringUtils.isEmpty(menuId)) {
            authenticationService.deleteAllAuthentication(menuId);
        }
    }

//    /**
//     * 消息消费失败的降级处理逻辑
//     *
//     * @param message
//     */
//    @ServiceActivator(inputChannel = "input")
//    public void error(Message<?> message) {
//        System.out.println("Message consumer failed, call fallback!");
//    }

}
