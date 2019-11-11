package com.spring.cloud.support.mvc;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ResourceDesc {
    /**
     * 模块描述
     * @return
     */
    String model() default "";
    /**
     * 功能名称
     * @return
     */
    String name() default "";
    /**
     * 功能描述
     * @return
     */
    String desc() default "";
}
