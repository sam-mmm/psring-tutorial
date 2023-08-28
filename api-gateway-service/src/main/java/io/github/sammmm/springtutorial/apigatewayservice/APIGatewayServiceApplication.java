package io.github.sammmm.springtutorial.apigatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration

public class APIGatewayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(APIGatewayServiceApplication.class, args);
    }
}
