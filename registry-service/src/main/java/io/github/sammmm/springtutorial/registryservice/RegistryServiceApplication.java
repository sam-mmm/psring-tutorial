package io.github.sammmm.springtutorial.registryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class RegistryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegistryServiceApplication.class, args);
    }
}
