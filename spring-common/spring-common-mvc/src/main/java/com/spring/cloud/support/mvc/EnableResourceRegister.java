package com.spring.cloud.support.mvc;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WebRequestMappingConfig.class})
public @interface EnableResourceRegister {
}
