/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltd.configs;

import javax.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Acer
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Chỉ định origin được phép
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Các phương thức được phép
                .allowCredentials(true) // Cho phép gửi cookies hoặc thông tin xác thực
                .allowedHeaders("Content-Type", "Authorization","*") // Các header được phép
                .exposedHeaders("Access-Control-Allow-Origin") // Các header được hiển thị
                .maxAge(3600);  // Thời gian cache kết quả của CORS
    }
}
