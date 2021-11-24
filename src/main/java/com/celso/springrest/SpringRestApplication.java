package com.celso.springrest;

import com.celso.springrest.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageConfig.class
})
public class SpringRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringRestApplication.class, args);
    }
}