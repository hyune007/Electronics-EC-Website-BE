package com.hyu.electronicsecwebsitebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ElectronicsEcWebsiteBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicsEcWebsiteBeApplication.class, args);
    }

}
