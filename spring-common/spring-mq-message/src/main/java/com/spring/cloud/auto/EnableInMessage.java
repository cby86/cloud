package com.spring.cloud.auto;

import com.spring.cloud.config.InMessageConfig;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(InMessageConfig.class)
@EnableBinding(Sink.class)
@EnableScheduling
public @interface EnableInMessage {
}
