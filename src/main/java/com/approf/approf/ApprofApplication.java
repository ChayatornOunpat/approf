package com.approf.approf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
public class ApprofApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApprofApplication.class, args);
    }

}
