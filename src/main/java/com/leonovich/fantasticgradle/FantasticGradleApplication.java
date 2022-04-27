package com.leonovich.fantasticgradle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class FantasticGradleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FantasticGradleApplication.class);
        log.info("Hello {}!", System.getProperty("user.name"));
    }
}
