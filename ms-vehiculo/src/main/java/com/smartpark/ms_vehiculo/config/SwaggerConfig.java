package com.smartpark.ms_vehiculo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Vehiculos - SmartPark")
                        .version("1.0.0")
                        .description("Microservicio para la gestion de vehiculos de la plataforma SmartPark")
                        .contact(new Contact()
                                .name("Equipo SmartPark - Duoc UC")
                                .email("contacto@smartpark.cl")
                                .url("https://smartpark.cl"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:8094").description("Servidor local"),
                        new Server().url("http://localhost:8080").description("A traves del API Gateway")));
    }
}