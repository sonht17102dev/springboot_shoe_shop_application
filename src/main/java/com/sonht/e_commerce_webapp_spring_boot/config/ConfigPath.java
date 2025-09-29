package com.sonht.e_commerce_webapp_spring_boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigPath implements WebMvcConfigurer {

    /*
     * Cấu hình đường dẫn cho tài nguyên tĩnh (ảnh sản phẩm)
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/images/**")
                .addResourceLocations("file:static/uploaded/images/");
    }
}
