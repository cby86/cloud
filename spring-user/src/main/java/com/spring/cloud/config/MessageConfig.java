//package com.spring.cloud.config;
//
//import com.rabbitmq.client.Channel;
//import com.spring.cloud.entity.Authentication;
//import com.spring.cloud.service.AuthenticationService;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//import org.springframework.cloud.stream.messaging.Sink;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//import java.util.List;
//
//
//@EnableBinding(Sink.class)
//@Configuration
//public class MessageConfig {
//    private static final int maxRetry = 3;
//    @Autowired
//    AuthenticationService authenticationService;
//    @StreamListener(target = Sink.INPUT,condition="headers['messageType']=='updateMenu'")
//    void menuUpdate(@Payload MenuMessage menuMessage,@Header("messageType") String header, @Header(AmqpHeaders.CHANNEL) Channel channel,
//                    @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag ,@Header("deliveryAttempt") int deliveryAttempt) throws Exception {
//        try {
//            authenticationService.updateAuthentications(menuMessage);
//            //手动确认
//            channel.basicAck(deliveryTag, false);
//        } catch (Exception ex) {
//            if (maxRetry==deliveryAttempt) {
//                channel.basicNack(deliveryTag, false, false);
//            }else {
//                throw ex;
//            }
//        }
//
//    }
//
//    @StreamListener(target = Sink.INPUT,condition="headers['messageType']=='deleteMenu'")
//    void deleteMenu(String menuId,@Header("messageType") String header) {
//        if (!StringUtils.isEmpty(menuId)) {
//            authenticationService.deleteAllAuthentication(menuId);
//        }
//    }
//
//}
