package com.spring.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

@Configuration
public class SwaggerConfig {
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("资源中心API").description("资源中心API").version("1.0").build();
    }

}