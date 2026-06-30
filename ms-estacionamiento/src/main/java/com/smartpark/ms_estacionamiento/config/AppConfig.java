package com.smartpark.ms_estacionamiento.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${ms.ubicacion.url}")
    private String ubicacionUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(ubicacionUrl)
                .build();
    }
}