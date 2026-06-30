package com.smartpark.ms_auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Autenticación - SmartPark")
                        .version("1.0.0")
                        .description("Microservicio para la autenticación y registro de usuarios de la plataforma SmartPark"));
    }
}