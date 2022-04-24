package com.example.brickscrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaRepositories
public class BrickApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(BrickApplication.class, args)));
    }

}
