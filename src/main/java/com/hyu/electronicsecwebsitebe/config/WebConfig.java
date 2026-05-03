package com.hyu.electronicsecwebsitebe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping ("/api/**")
////                .allowedOrigins ("http://localhost:8080")
//                .allowedOrigins ("https://ec-website-fe-312564370609.asia-southeast1.run.app", "https://ubraintech.store")
//                .allowedMethods ("GET", "POST", "PUT", "DELETE", "OPTIONS");
//    }

    @Value("${app.upload-dir:/app/uploads}")
    private String uploadBaseDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = Paths.get(uploadBaseDir, "photos").toUri().toString();
        if (!location.endsWith("/")) {
            location = location + "/";
        }
        registry.addResourceHandler("/photos/**")
                .addResourceLocations(location);
    }
}
