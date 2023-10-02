package org.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/task-service/v3/api-docs").uri("lb://task-service"))
                .route(r -> r.path("/tasks/**").uri("lb://task-service"))
                .route(r -> r.path("/keycloak-service/v3/api-docs").uri("lb://keycloak-service"))
                .route(r -> r.path("/users/**").uri("lb://keycloak-service"))
                .route(r -> r.path("/groups/**").uri("lb://keycloak-service"))
                .build();
    }
}