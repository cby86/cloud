package com.spring.cloud.auto;

import com.spring.cloud.config.OutMessageConfig;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = OutMessageConfig.class)
@EnableBinding(Source.class)
@EnableScheduling
public @interface EnableOutMessage {
}
