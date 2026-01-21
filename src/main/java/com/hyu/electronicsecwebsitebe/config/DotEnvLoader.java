package com.hyu.electronicsecwebsitebe.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotEnvLoader implements EnvironmentPostProcessor {

    public DotEnvLoader() {

    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // Cấu hình và tải nội dung từ tập tin ..env tại thư mục gốc
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();

        // Chuyển đổi dữ liệu từ Dotenv sang cấu trúc Map
        Map<String, Object> dotenvMap = new HashMap<>();
        dotenv.entries().forEach(e -> {
            dotenvMap.put(e.getKey(), e.getValue());
        });

        // Tích hợp biến môi trường từ ..env vào Spring Environment với độ ưu tiên cao nhất
        environment.getPropertySources()
                .addFirst(new MapPropertySource("dotenvProperties", dotenvMap));
    }

}
